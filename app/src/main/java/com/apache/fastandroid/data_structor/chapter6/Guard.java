package com.apache.fastandroid.data_structor.chapter6;

/**
 * Created by Jerry on 2020-04-12.
 */
public class Guard {
    public static void main(String[] args) {
        int[] a = new int[]{4, 2, 3, 5, 9, 6};
        System.out.println("value1: "+find(a,6,7));
        System.out.println("value2: "+find(a,6,6));


    }



    // 在数组a中，查找key，返回key所在的位置
// 其中，n表示数组a的长度
// 我举2个例子，你可以拿例子走一下代码
// a = {4, 2, 3, 5, 9, 6}  n=6 key = 7
// a = {4, 2, 3, 5, 9, 6}  n=6 key = 6
    static int find(int[] a, int n, int key) {
        if(a == null || n <= 0) {
            return -1;
        }

        // 这里因为要将a[n-1]的值替换成key，所以要特殊处理这个值
        if (a[n-1] == key) {
            return n-1;
        }


        // 把a[n-1]的值临时保存在变量tmp中，以便之后恢复。tmp=6。
        // 之所以这样做的目的是：希望find()代码不要改变a数组中的内容
        int tmp = a[n-1];
        // 把key的值放到a[n-1]中，此时a = {4, 2, 3, 5, 9, 7}
        a[n-1] = key;

        int i = 0;
        // while 循环比起代码一，少了i<n这个比较操作
        while (a[i] != key) {
            ++i;
        }

        // 恢复a[n-1]原来的值,此时a= {4, 2, 3, 5, 9, 6}
        a[n-1] = tmp;

        if (i == n-1) {
            // 如果i == n-1说明，在0...n-2之间都没有key，所以返回-1
            return -1;
        } else {
            // 否则，返回i，就是等于key值的元素的下标
            return i;
        }
    }
}
