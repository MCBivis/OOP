package org.example;

public class HeapSort {
    public static void swap(int[] arr, int i, int j) {//обмен местами элементов массива
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void heapsort(int[] arr){//пирамидальная сортировка
        int len = arr.length;
        if (heapinit(arr,len)==0){
            return;
        }
        swap(arr,0,--len);
        while (len>1){
            heapify(arr,0,len);
            swap(arr,0,--len);
        }
    }
    public static int heapinit(int[] arr,int len){//инициализация пирамиды
        if (len<2){
            return 0;
        }
        for (int i=(len/2)-1;i>=0;i--){
            heapify(arr,i,len);
        }
        return 1;
    }
    public static void heapify(int[] arr,int root,int len){//приведение к пирамидальному виду
        if (root<len/2){
            int right= (root+1)*2;
            int left= right-1;
            if (right >= len){
                if (arr[left]>arr[root]){
                    swap(arr,left,root);
                    heapify(arr,left,len);
                }
            }else{
                if (arr[left]>arr[root]){
                    swap(arr,left,root);
                    heapify(arr,left,len);
                }
                if (arr[right]>arr[root]){
                    swap(arr,right,root);
                    heapify(arr,right,len);
                }
            }
        }
    }
}
