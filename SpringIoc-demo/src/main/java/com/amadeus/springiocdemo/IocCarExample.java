package com.amadeus.springiocdemo;

public class IocCarExample {
    public static void main(String[] args) {
        Tire tire = new Tire(15);
        Bottom bottom = new Bottom(tire);
        Framework framework = new Framework(bottom);
        Car car = new Car(framework);
        car.run();
    }
}
