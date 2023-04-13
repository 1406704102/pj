package com.pangjie.sort;

public class test {
    public static void main(String[] args) {


        int[] ints = {1, 3, 6, 7, 4, 23, 1, 456, 123, 3, 6, 4, 3, 64};

        for (int i = 0; i < ints.length; i++) {
            for (int i1 = 0; i1 < ints.length - i - 1; i1++) {
                if (ints[i1] > ints[i1+1]) {
                    int temp = ints[i1+1];
                    ints[i1+1] = ints[i1];
                    ints[i1] = temp;
                }
            }
        }
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+",");
        }



        int[] l = {1, 3, 6, 7, 4, 23, 1, 456, 123, 3, 6, 4, 3, 64};
        for (int i = 0; i < l.length; i++) {
            System.out.print(l[i]+",");
        }
        System.out.println("");
        QuickSort.quickSort(l);
        for (int i = 0; i < l.length; i++) {
            System.out.print(l[i]+",");
        }

    }
}
