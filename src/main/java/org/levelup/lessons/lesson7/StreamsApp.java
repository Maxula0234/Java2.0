package org.levelup.lessons.lesson7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsApp {
    public static void main(String[] args) {

        //StreamApi + lambda
        Collection<String> logins = new ArrayList<>();
        logins.add("qwertt");
        logins.add("qwertt1111");
        logins.add("qwefdsfdsft1111");
        logins.add("qwefdsfdsft1111");
        logins.add("233332");
        logins.add("eweadx");
        logins.add("ewex");
        logins.add("gfg");
        logins.add("ewadx");
        logins.add("vxcvcxv");

        //filters
        //login, wich length <= 6

        //until 8 java
        Collection<String> shortLogins = new ArrayList<>();
        for (String login : logins) {
            if (login.length() < 6) {
                shortLogins.add(login);
            }
        }

        Collection<String> shortLoginUserStream = logins.stream()
                //.filter(new ShortLoginPredicar())

                //Используем lambda
                //lmd: (args without types) -> {override method body}
                .filter(s -> s.length() < 6)//если true то остается в стриме
                .collect(Collectors.toList()); //Stream<String> -> List<String>

        System.out.println(shortLogins);
        System.out.println("Filtered using stream: " + shortLoginUserStream);


        //find min

        int min = logins.stream()
                .mapToInt(String::length)// Function<T,R>  -> R apply(T t )
                .min()
                .orElse(0);

        System.out.println("Min = " + min);


        //Stream types:
        //              -Stream
        //              -intStream
        //              -longStream
        //              -doubleStream


        //arg -> expression(arg)
        // forEach(el -> System.out.println(el);


        //arg -> arg.method() -> ArgClass::method -> method reference
        // map(string-> string.toUpperCase()) -> String::toUpperCase



        String minLogin = logins.stream()
                .min((fLogin,sLogin) -> Integer.compare(fLogin.length(),sLogin.length()))
                .orElse(null);

        //Group by length
        //Collection<String> -> Map<Integer,Collection<String>>
        Map<Integer, List<String>> groupByLength = logins.stream()
                .collect(Collectors.groupingBy(String::length));
        //Find min map key

        int minLengthKey = groupByLength.keySet().stream()
                .mapToInt(key->key)
                .min()
                .orElse(0);
        List<String> loginWithTheShortesLength = groupByLength.get(minLengthKey);
    }

    static class ShortLoginPredicar implements Predicate<String> {
        @Override
        public boolean test(String s) {
            return s.length() < 6;
        }
    }
}
