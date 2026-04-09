package org.example.Benchmarking;

import org.example.BST;
import org.example.RBTree;
import org.example.quick_sort;


public class BenchMark {

    private final int[] array1;
    private final int[] lockup1;
    private final int[] toBeDeleted1;

    private final int[] array5;
    private final int[] lockup5;
    private final int[] toBeDeleted5;

    private final int[] array10;
    private final int[] lockup10;
    private final int[] toBeDeleted10;

    private final int[] arrayRand;
    private final int[] lockupRand;
    private final int[] toBeDeletedRand;

    // ---------------------------------------------------------------
    // Constructor — generate all inputs once
    // ---------------------------------------------------------------
    public BenchMark() {
        BenchMarkInput input = new BenchMarkInput();

        array1       = input.GenerateArray(1);
        lockup1      = input.GenerateLockup(false);
        toBeDeleted1 = input.GenerateToBeDeleted();

        array5       = input.GenerateArray(5);
        lockup5      = input.GenerateLockup(false);
        toBeDeleted5 = input.GenerateToBeDeleted();

        array10       = input.GenerateArray(10);
        lockup10      = input.GenerateLockup(false);
        toBeDeleted10 = input.GenerateToBeDeleted();

        arrayRand       = input.GenerateRandomArray();
        lockupRand      = input.GenerateLockup(true);
        toBeDeletedRand = input.GenerateToBeDeleted();
    }

    //  1 %  nearly-sorted


    public long[] MeasureInsert1(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = new RBTree();
                long start = System.nanoTime();
                for (int j = 0; j < array1.length; j++) {
                    rbt.insert(array1[j]);               // FIX: j not i
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = new BST();
                long start = System.nanoTime();
                for (int j = 0; j < array1.length; j++) {
                    bst.insert(array1[j]);               // FIX: j not i
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureDelete1(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = buildRBT(array1);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted1.length; j++) {
                    rbt.delete(toBeDeleted1[j]);         // FIX: j not i; reuse same tree
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = buildBST(array1);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted1.length; j++) {
                    bst.delete(toBeDeleted1[j]);
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureContain1(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            RBTree rbt = buildRBT(array1);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup1.length; j++) {
                    rbt.contains(lockup1[j]);            // FIX: j not i
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            BST bst = buildBST(array1);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup1.length; j++) {
                    bst.contains(lockup1[j]);
                }
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    /*
     * isRBT == 1  → RBT inOrder
     * isRBT == 0  → BST inOrder
     * isRBT == -1 → QuickSort
     */
    public long[] MeasureSort1(int isRBT) {
        long[] times = new long[5];
        if (isRBT == 1) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                RBTree rbt = buildRBT(array1);   // FIX: build pre-loaded tree; include build time
                rbt.inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else if (isRBT == 0) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                BST bst = buildBST(array1);      // FIX: build pre-loaded tree; include build time
                bst.inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int[] arr = array1.clone();      // clone so sort operates on fresh copy each run
                long start = System.nanoTime();
                quick_sort.sort(arr);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    //  5 %  nearly-sorted

    public long[] MeasureInsert5(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = new RBTree();
                long start = System.nanoTime();
                for (int j = 0; j < array5.length; j++) rbt.insert(array5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = new BST();
                long start = System.nanoTime();
                for (int j = 0; j < array5.length; j++) bst.insert(array5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureDelete5(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = buildRBT(array5);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted5.length; j++) rbt.delete(toBeDeleted5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = buildBST(array5);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted5.length; j++) bst.delete(toBeDeleted5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureContain5(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            RBTree rbt = buildRBT(array5);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup5.length; j++) rbt.contains(lockup5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            BST bst = buildBST(array5);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup5.length; j++) bst.contains(lockup5[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureSort5(int isRBT) {
        long[] times = new long[5];
        if (isRBT == 1) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildRBT(array5).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else if (isRBT == 0) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildBST(array5).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int[] arr = array5.clone();
                long start = System.nanoTime();
                quick_sort.sort(arr);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    //  10 %  nearly-sorted

    public long[] MeasureInsert10(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = new RBTree();
                long start = System.nanoTime();
                for (int j = 0; j < array10.length; j++) rbt.insert(array10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = new BST();
                long start = System.nanoTime();
                for (int j = 0; j < array10.length; j++) bst.insert(array10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureDelete10(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = buildRBT(array10);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted10.length; j++) rbt.delete(toBeDeleted10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = buildBST(array10);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeleted10.length; j++) bst.delete(toBeDeleted10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureContain10(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            RBTree rbt = buildRBT(array10);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup10.length; j++) rbt.contains(lockup10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            BST bst = buildBST(array10);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockup10.length; j++) bst.contains(lockup10[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureSort10(int isRBT) {
        long[] times = new long[5];
        if (isRBT == 1) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildRBT(array10).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else if (isRBT == 0) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildBST(array10).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int[] arr = array10.clone();
                long start = System.nanoTime();
                quick_sort.sort(arr);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    //  fully random

    public long[] MeasureInsertRand(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = new RBTree();
                long start = System.nanoTime();
                for (int j = 0; j < arrayRand.length; j++) rbt.insert(arrayRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = new BST();
                long start = System.nanoTime();
                for (int j = 0; j < arrayRand.length; j++) bst.insert(arrayRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureDeleteRand(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            for (int i = 0; i < 8; i++) {
                RBTree rbt = buildRBT(arrayRand);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeletedRand.length; j++) rbt.delete(toBeDeletedRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                BST bst = buildBST(arrayRand);
                long start = System.nanoTime();
                for (int j = 0; j < toBeDeletedRand.length; j++) bst.delete(toBeDeletedRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureContainRand(boolean isRBT) {
        long[] times = new long[5];
        if (isRBT) {
            RBTree rbt = buildRBT(arrayRand);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockupRand.length; j++) rbt.contains(lockupRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            BST bst = buildBST(arrayRand);
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < lockupRand.length; j++) bst.contains(lockupRand[j]);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }

    public long[] MeasureSortRand(int isRBT) {
        long[] times = new long[5];
        if (isRBT == 1) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildRBT(arrayRand).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else if (isRBT == 0) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                buildBST(arrayRand).inOrder();
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int[] arr = arrayRand.clone();
                long start = System.nanoTime();
                quick_sort.sort(arr);
                if (i >= 3) times[i - 3] = System.nanoTime() - start;
            }
        }
        return times;
    }


    private RBTree buildRBT(int[] data) {
        RBTree rbt = new RBTree();
        for (int v : data) rbt.insert(v);
        return rbt;
    }

    private BST buildBST(int[] data) {
        BST bst = new BST();
        for (int v : data) bst.insert(v);
        return bst;
    }
}