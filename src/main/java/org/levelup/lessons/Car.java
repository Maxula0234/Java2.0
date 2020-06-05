package org.levelup.lessons;

public class Car {
    private String model = "Bmw";
    private String wheels = "r18";
    private String weigth = "2000";

    public Car(){
    }

    @Override
    public String toString()
    {
        return this.model +" "+this.wheels +" "+this.weigth;
    }
}
