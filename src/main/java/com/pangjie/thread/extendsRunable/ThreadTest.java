package com.pangjie.thread.extendsRunable;

public class ThreadTest extends Thread{

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName()+"  " +i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "  : " + i);
            if (i == 20) {
                new ThreadTest().start();
                new ThreadTest().start();
            }
        }
    }
}
