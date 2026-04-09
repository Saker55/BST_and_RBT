package org.example;

import org.example.Benchmarking.BenchMark;
import org.example.Benchmarking.BenchMarkCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Benchmarkrunner {

    private static final Logger log = LoggerFactory.getLogger(Benchmarkrunner.class);

    public static void main(String[] args) {

        String outputDir = (args.length > 0) ? args[0] : "results";

        java.io.File dir = new java.io.File(outputDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            log.info("Output directory created: {} ({})", outputDir, created);
        }

        String csvPath = BenchMarkCSV.timestampedPath(outputDir);
        log.info("Benchmark starting — output: {}", csvPath);

        BenchMark    bm  = new BenchMark();
        BenchMarkCSV csv = new BenchMarkCSV(csvPath);

        try {
            csv.open();

            // ---- 1% nearly-sorted ----
            log.info("Running 1% nearly-sorted distribution");
            csv.writeSection("INSERT",  "1pct", bm.MeasureInsert1(true),  bm.MeasureInsert1(false));
            csv.writeSection("DELETE",  "1pct", bm.MeasureDelete1(true),  bm.MeasureDelete1(false));
            csv.writeSection("CONTAINS","1pct", bm.MeasureContain1(true), bm.MeasureContain1(false));
            csv.writeSort("1pct", bm.MeasureSort1(1), bm.MeasureSort1(0), bm.MeasureSort1(-1));

            // ---- 5% nearly-sorted ----
            log.info("Running 5% nearly-sorted distribution");
            csv.writeSection("INSERT",  "5pct", bm.MeasureInsert5(true),  bm.MeasureInsert5(false));
            csv.writeSection("DELETE",  "5pct", bm.MeasureDelete5(true),  bm.MeasureDelete5(false));
            csv.writeSection("CONTAINS","5pct", bm.MeasureContain5(true), bm.MeasureContain5(false));
            csv.writeSort("5pct", bm.MeasureSort5(1), bm.MeasureSort5(0), bm.MeasureSort5(-1));

            // ---- 10% nearly-sorted ----
            log.info("Running 10% nearly-sorted distribution");
            csv.writeSection("INSERT",  "10pct", bm.MeasureInsert10(true),  bm.MeasureInsert10(false));
            csv.writeSection("DELETE",  "10pct", bm.MeasureDelete10(true),  bm.MeasureDelete10(false));
            csv.writeSection("CONTAINS","10pct", bm.MeasureContain10(true), bm.MeasureContain10(false));
            csv.writeSort("10pct", bm.MeasureSort10(1), bm.MeasureSort10(0), bm.MeasureSort10(-1));

            // ---- fully random ----
            log.info("Running fully-random distribution");
            csv.writeSection("INSERT",  "random", bm.MeasureInsertRand(true),  bm.MeasureInsertRand(false));
            csv.writeSection("DELETE",  "random", bm.MeasureDeleteRand(true),  bm.MeasureDeleteRand(false));
            csv.writeSection("CONTAINS","random", bm.MeasureContainRand(true), bm.MeasureContainRand(false));
            csv.writeSort("random", bm.MeasureSortRand(1), bm.MeasureSortRand(0), bm.MeasureSortRand(-1));

            log.info("Benchmark complete — results written to {}", csvPath);

        } catch (IOException e) {
            log.error("Failed to write CSV: {}", e.getMessage(), e);
        } finally {
            csv.close();
        }
    }
}