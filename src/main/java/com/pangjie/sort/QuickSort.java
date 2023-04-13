package com.pangjie.sort;

public class QuickSort {
    public static void quickSort(int[] a) {
        quickSortRecursive(a, 0, a.length-1);
    }

    /**
     * 快速排序递归函数，p,r为下标。
     *
     * @param a
     * @param p
     * @param r
     */
    public static void quickSortRecursive(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        // 获取分区点
        int q = partition(a, p, r);
        quickSortRecursive(a, p, q - 1);
        quickSortRecursive(a, q + 1, r);
    }

    public static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                i++;
            }
        }
        int t = a[i];
        a[i] = a[r];
        a[r] = t;
        System.out.println("基准数: " + a[i]);
        for (int i2 = 0; i2 < a.length; i2++) {
            System.out.print(a[i2]+",");
        }
        System.out.println();
        return i;
    }
}
