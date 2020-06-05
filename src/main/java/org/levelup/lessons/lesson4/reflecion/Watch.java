package org.levelup.lessons.lesson4.reflecion;

public class Watch {

    public int radius;
    public String watchName;
    private int diameter;

    private int hour;
    private int minute;

    public Watch(){};
    public Watch(String watchName,int radius){
        this.watchName = watchName;
        this.radius = radius;
        this.diameter = radius * 2;
    };

    public void changeTime(int hour,int minute){
        System.out.println("change invoked...");
        this.hour = hour;
        this.minute = minute;
    }
}
