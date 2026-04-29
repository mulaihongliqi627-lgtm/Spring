package com.amadeus.springiocdemo;

public class Car {
    //车身
    private static Framework framework;

    public Car(){
        //构建底盘
        framework = new Framework();
        System.out.println("car init...");
    }

    //启动汽车car
    public void run(){
        System.out.println("car is running");
    }
}
