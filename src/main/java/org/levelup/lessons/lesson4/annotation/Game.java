package org.levelup.lessons.lesson4.annotation;

import java.lang.reflect.Field;
import java.util.Random;

public class Game {
    public static void main(String[] args) {
        Dice dice = createDice();
        System.out.println("Cube value: " + dice.getCubeValue());
        System.out.println("SecondCube value: " + dice.getSecondCube());
        System.out.println("ThirdCube value: " + dice.getThirdCube());
    }
    static Dice createDice() {
        Class<?> diceClass = Dice.class;
        try {
            Dice object = (Dice) diceClass.getConstructor().newInstance();
            Field[] fields = diceClass.getDeclaredFields();
            for (Field field : fields){
                RandomInt annotation = field.getAnnotation(RandomInt.class);
                if (annotation != null){
                    Random random = new Random();
                    int min = annotation.min();
                    int max = annotation.max();

                    int randomInt = random.nextInt(max - min + 1)+min;//

                    field.setAccessible(true);
                    field.set(object,randomInt);
                }
            }
            return object;
        } catch (Exception e){
            throw  new RuntimeException();
        }
    }
}
