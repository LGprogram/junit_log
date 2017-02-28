package com.kaishengit.test;

/**
 * Created by liu on 2017/2/15.
 */
class Abs{
//    对于升序数组int a{} 编写折半查找算法找到int b 在数组a中得下标
    public static int find(int[]a, int b){
        //前提b在a数组中确实存在
        int length= a.length;
        boolean result = false;
        int p = length/2;
        while(!result){
// while循环每次执行循环体之前，先判断循环条件，当条件成立时执行循环体，
// 当条件不成立时跳出循环：此处当a[p]=b时进入判断条件result=true结束循环
            if(a[p]<b){
                p=(p+length)/2;
            }else if(a[p]>b){
                p=p/2;
            }else{
                result=true;
            }
        }
        return p;
    }
}
/*写程序实现count（n）=l！+2！  +…+n！
        int count（int n）{
        int result=0；
        …
        Return result；
        }*/
class Bbs{
    public static int count(int n){
        int result = 0;

        for(int i=1;i<=n;i++){
            int a =1;
            for(int j = 1;j<=i;j++){
               a*=j;
            }
            result+=a;
        }

        return result;
    }
}
class Ccs{
    public static int count(int n){
        int result = 0;
        for(int i=1;i<=n;i++){
            int a=1;
            for(int j=1;j<=i;j++){
                a*=j;
            }
            result+=a;
        }
        return result;
    }
}

public class MianShiTest {
    public static void main(String args[]){
        int a[] = new int[] {1,2,3,4,5,6};
//        int a[]= {1,2,3,4,5,6};
       /* int a[] = new int[6];
        a[0]= 1;*/
       int b = 5;
        int p = Abs.find(a,b);
        System.out.println(a.length);
        System.out.println(p);
        int result = Bbs.count(b);
        System.out.println(result);


    }
}
