package org.levelup.lessons;

public class Home {
    private String room = "kitchen";
    private String window = "Open";
    private String door = "Close";

    public Home(){
    }

    @Override
    public String toString()
    {
        return this.room+" "+this.window+" "+this.door;
    }
}
