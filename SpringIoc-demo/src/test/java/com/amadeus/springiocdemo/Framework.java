package com.amadeus.springiocdemo;

public class Framework {
    //底盘
    private static Bottom bottom;

    public Framework (){
        bottom = new Bottom();
        System.out.println("framework init...");
    }

}
