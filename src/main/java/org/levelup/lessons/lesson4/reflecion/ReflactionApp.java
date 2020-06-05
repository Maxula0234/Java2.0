package org.levelup.lessons.lesson4.reflecion;

import java.lang.reflect.*;

public class ReflactionApp {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Watch watchObject = new Watch();
        Watch watch = new Watch();

        //1var
        Class<?> watchClass = watchObject.getClass();
        Class<?> watchClass2 = watch.getClass();

        //2var
        Class<?> watchFromLiteral= Watch.class;
        System.out.println(watchClass == watchFromLiteral);

        //ClassLoader
        ClassLoader watchClassLoader = watchClass.getClassLoader();
        System.out.println(watchClassLoader.getName());

        Class<?> integerClass = Integer.class;
        System.out.println("Integer class loader: " + integerClass.getClassLoader());

        System.out.println("Name of class: " + watchClass.getName());
        int classmodifures = watchClass.getModifiers();
        System.out.println("Modifer of class: " + Modifier.toString(classmodifures));

        //Field
        //getfield(s) все публичные поля вернет
        Field[] publicField = watchClass.getFields();
        System.out.println("Public fields of class Watch: ");
        for (Field field : publicField){
            System.out.println(field.getName());
        }

        Field radiusField = watchClass.getField("radius");
        radiusField.set(watchObject,11);
        System.out.println("Watch radius: " + watchObject.radius);

        //getDeclaredField(s) все поля
        Field declaredField = watchClass.getDeclaredField("diameter");
        declaredField.setAccessible(true);
        declaredField.set(watchObject,22);
        System.out.println("Watch diameter: " + declaredField.get(watchObject));

        Method changeTimeMethod = watchClass.getDeclaredMethod("changeTime",int.class,int.class); //Integer.class, Integer.class- change
        changeTimeMethod.setAccessible(true);
        Object result = changeTimeMethod.invoke(watchObject,20,15);
        System.out.println(result);


        Constructor<?> watchConstructor = watchClass.getDeclaredConstructor(String.class,int.class);
        watchConstructor.setAccessible(true);
        Watch newWatch = (Watch)watchConstructor.newInstance("appleWatch",10);
        System.out.println(newWatch.radius);
        System.out.println(newWatch.watchName);


    }
}
