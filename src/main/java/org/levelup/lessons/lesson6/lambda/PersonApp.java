package org.levelup.lessons.lesson6.lambda;

import org.hibernate.mapping.Collection;

import javax.naming.CompositeName;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PersonApp {
    public static void main(String[] args) {

    }

    static void sort(List<Person> people){
        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };


        Comparator<Person> lamdaComporator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        Comparator<Person> lamdaComporatorNew = (obj1,obj2) -> obj1.getName().compareTo(obj2.getName());
    }
}
