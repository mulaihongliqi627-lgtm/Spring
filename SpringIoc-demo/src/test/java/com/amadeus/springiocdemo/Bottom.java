package com.amadeus.springiocdemo;

public class Bottom {
    //轮胎
    private static Tire tire;

    public Bottom(){
        tire = new Tire();
        System.out.println("bottom init...");
    }
}
