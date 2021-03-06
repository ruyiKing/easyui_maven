package com.yrw.test.list;
import java.util.*;   
public class ListDemo {   
     static final int N=50000;   
     static long timeList(List<Object> list){   
     long start=System.currentTimeMillis();   
     Object o = new Object();   
     for(int i=0;i<N;i++)   
         list.add(0, o);   
     return System.currentTimeMillis()-start;   
     }   
     public static void main(String[] args) {   
         System.out.println("ArrayList耗时："+timeList(new ArrayList<Object>()));   
         System.out.println("LinkedList耗时："+timeList(new LinkedList<Object>()));   
     }   
} 