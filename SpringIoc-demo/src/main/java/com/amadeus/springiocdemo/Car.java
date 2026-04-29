package com.amadeus.springiocdemo;

public class Car {
    private Framework framework;
    public Car(Framework framework){
        this.framework = framework;
        System.out.println("car init...");
    }

    public void run(){
        System.out.println("car is running...");
    }
}
