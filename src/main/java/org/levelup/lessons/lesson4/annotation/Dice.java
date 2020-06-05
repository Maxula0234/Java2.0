package org.levelup.lessons.lesson4.annotation;

public class Dice {

    @RandomInt(max = 6, min = 1)
    private int cubeValue;
   private int secondCube;

   @RandomInt(max = 100, min = 10)
   private int thirdCube;

    public int getCubeValue() {
        return cubeValue;
    }
    public int getSecondCube(){
        return secondCube;
    }
    public int getThirdCube(){
        return thirdCube;
    }

}
