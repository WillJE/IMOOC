package com.Sum;

public class Sum {
    public static int sum(int[] arr){
        return sum(arr, 1);
    }

    /**
     *
     * @param arr
     * @param l 左边界，即从第几个下标开始相加
     * @return
     */
    private static int sum(int[] arr, int l){
        if(l == arr.length){
            return 0;
        }
        return arr[l] + sum(arr,l+1);
    }
}
