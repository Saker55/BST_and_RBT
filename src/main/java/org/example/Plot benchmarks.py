#!/usr/bin/env python3
"""
plot_benchmarks.py
==================
Reads the CSV produced by BenchMarkRunner and generates four grouped bar
charts (INSERT, DELETE, CONTAINS, SORT) plus a speed-up line chart.

Usage:
    python plot_benchmarks.py benchmark_YYYYMMDD_HHMMSS.csv

Output:
    figures/insert.png
    figures/delete.png
    figures/contains.png
    figures/sort.png
    figures/speedup.png
    figures/all_operations.png   (2x3 subplot grid — use this in the report)

Requirements:
    pip install pandas matplotlib
"""

import sys
import os
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as mticker
import numpy as np

# ── colour palette ────────────────────────────────────────────────────────────
COLORS = {
    "RBT":       "#2980B9",   # blue
    "BST":       "#E74C3C",   # red
    "QuickSort": "#27AE60",   # green
}
DIST_LABELS = {
    "1pct":   "1% mis-\nplaced",
    "5pct":   "5% mis-\nplaced",
    "10pct":  "10% mis-\nplaced",
    "random": "Random",
}
DIST_ORDER = ["1pct", "5pct", "10pct", "random"]

# ── helpers ───────────────────────────────────────────────────────────────────

def load(csv_path: str) -> pd.DataFrame:
    df = pd.read_csv(csv_path)
    df.columns = df.columns.str.strip()
    return df


def pivot_operation(df: pd.DataFrame, operation: str) -> pd.DataFrame:
    """Return mean_ms for each (distribution, structure) pair."""
    sub = df[df["operation"] == operation].copy()
    piv = sub.pivot_table(index="distribution", columns="structure",
                          values="mean_ms", aggfunc="mean")
    piv = piv.reindex(DIST_ORDER)
    return piv


def bar_chart(ax, piv: pd.DataFrame, title: str, structures: list[str]):
    x = np.arange(len(DIST_ORDER))
    width = 0.25
    offsets = np.linspace(-(len(structures)-1)/2, (len(structures)-1)/2, len(structures))

    for offset, struct in zip(offsets, structures):
        values = [piv.loc[d, struct] if d in piv.index and struct in piv.columns
                  else 0.0 for d in DIST_ORDER]
        bars = ax.bar(x + offset * width, values, width,
                      label=struct, color=COLORS.get(struct, "grey"),
                      edgecolor="white", linewidth=0.5)

    ax.set_title(title, fontsize=11, fontweight="bold")
    ax.set_ylabel("Mean time (ms)", fontsize=9)
    ax.set_xticks(x)
    ax.set_xticklabels([DIST_LABELS[d] for d in DIST_ORDER], fontsize=8)
    ax.yaxis.set_major_formatter(mticker.FormatStrFormatter("%.1f"))
    ax.legend(fontsize=8)
    ax.grid(axis="y", linestyle="--", alpha=0.4)
    ax.spines[["top", "right"]].set_visible(False)


def speedup_chart(ax, df: pd.DataFrame):
    """BST mean / RBT mean for INSERT, DELETE, CONTAINS across distributions."""
    ops = ["INSERT", "DELETE", "CONTAINS"]
    markers = ["o", "s", "^"]
    for op, marker in zip(ops, markers):
        piv = pivot_operation(df, op)
        if "RBT" not in piv.columns or "BST" not in piv.columns:
            continue
        speedups = piv["BST"] / piv["RBT"].replace(0, np.nan)
        ax.plot(range(len(DIST_ORDER)), speedups.values,
                marker=marker, label=op, linewidth=1.5)

    ax.axhline(1.0, color="black", linestyle="--", linewidth=0.8, alpha=0.5)
    ax.set_title("RBT Speed-up over BST\n(BST mean / RBT mean; >1 = RBT faster)",
                 fontsize=10, fontweight="bold")
    ax.set_ylabel("Speed-up factor", fontsize=9)
    ax.set_xticks(range(len(DIST_ORDER)))
    ax.set_xticklabels([DIST_LABELS[d] for d in DIST_ORDER], fontsize=8)
    ax.legend(fontsize=8)
    ax.grid(linestyle="--", alpha=0.4)
    ax.spines[["top", "right"]].set_visible(False)


# ── main ──────────────────────────────────────────────────────────────────────

def main():
    if len(sys.argv) < 2:
        print("Usage: python plot_benchmarks.py <path/to/benchmark.csv>")
        sys.exit(1)

    csv_path = sys.argv[1]
    if not os.path.exists(csv_path):
        print(f"File not found: {csv_path}")
        sys.exit(1)

    os.makedirs("figures", exist_ok=True)
    df = load(csv_path)

    # ── individual charts ─────────────────────────────────────────────────────
    specs = [
        ("INSERT",   ["RBT", "BST"],                "insert.png"),
        ("DELETE",   ["RBT", "BST"],                "delete.png"),
        ("CONTAINS", ["RBT", "BST"],                "contains.png"),
        ("SORT",     ["RBT", "BST", "QuickSort"],   "sort.png"),
    ]

    for op, structs, fname in specs:
        fig, ax = plt.subplots(figsize=(6, 4))
        piv = pivot_operation(df, op)
        bar_chart(ax, piv, f"{op} — Mean Time by Distribution", structs)
        fig.tight_layout()
        fig.savefig(f"figures/{fname}", dpi=150)
        plt.close(fig)

    # ── speed-up chart ────────────────────────────────────────────────────────
    fig, ax = plt.subplots(figsize=(6, 4))
    speedup_chart(ax, df)
    fig.tight_layout()
    fig.savefig("figures/speedup.png", dpi=150)
    plt.close(fig)

    # ── combined 2×3 grid for the report ─────────────────────────────────────
    fig, axes = plt.subplots(2, 3, figsize=(14, 8))
    fig.suptitle("BST vs Red-Black Tree — Benchmark Results", fontsize=13, fontweight="bold")

    bar_chart(axes[0][0], pivot_operation(df, "INSERT"),
              "INSERT", ["RBT", "BST"])
    bar_chart(axes[0][1], pivot_operation(df, "DELETE"),
              "DELETE", ["RBT", "BST"])
    bar_chart(axes[0][2], pivot_operation(df, "CONTAINS"),
              "CONTAINS", ["RBT", "BST"])
    bar_chart(axes[1][0], pivot_operation(df, "SORT"),
              "SORT (build + traversal)", ["RBT", "BST", "QuickSort"])
    speedup_chart(axes[1][1], df)
    axes[1][2].axis("off")   # spare cell — leave blank

    fig.tight_layout(rect=[0, 0, 1, 0.96])
    fig.savefig("figures/all_operations.png", dpi=150)
    plt.close(fig)

    print("Figures written to figures/")


if __name__ == "__main__":
    main()