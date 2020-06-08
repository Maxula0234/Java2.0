package org.levelup.lessons.lesson6;

import org.hibernate.SessionFactory;
import org.levelup.chat.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HibernateMethodsExample {

    public static void main(String[] args) throws IOException {
        SessionFactory factory = HibernateUtils.getFactory();
        UserDao1 dao = new UserDao1(factory);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter login");
//        String login = reader.readLine();
//        System.out.println("Enter firstName");
//        String firstName = reader.readLine();
//        System.out.println("Enter lastName");
//        String lastName = reader.readLine();
        System.out.println("Enter id");
        Integer id = Integer.parseInt(reader.readLine());

        dao.get(id);
        dao.load(id);
//        dao.updateUser(id,login,firstName,lastName);
        factory.close();
    }
}
