package org.levelup.chat;

import org.levelup.chat.jdbc.DatabaseConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

public class UserService {

    public static void createUser() throws SQLException, IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = DatabaseConnectionFactory.createConnection();

        PreparedStatement insertUser = connection.prepareStatement(
                "insert into users(login,first_name,last_name) values(?,?,?)"
        );

        System.out.println("Enter first name: - ");
        String name = buf.readLine();
        System.out.println("Enter last name: - ");
        String lastName = buf.readLine();

        insertUser.setString(2, name);
        System.out.println("Enter last name: - ");
        insertUser.setString(3, lastName);
        String login = name.charAt(0) + lastName;
        insertUser.setString(1, login);
        insertUser.executeUpdate();
        System.out.println(String.format("Insert users - %s %s [%s]",name,lastName,login));
        connection.close();
    }

    public static void removeUser(int id) throws SQLException {
        Connection connection = DatabaseConnectionFactory.createConnection();
        PreparedStatement query = connection.prepareStatement("delete from users where id = ?");
        query.setInt(1, id);
        query.execute();
    }

//    public static void findall() throws SQLException {
//        Connection connection = DatabaseConnectionFactory.createConnection();
//        Statement selectUsers = connection.createStatement();
//        ResultSet result = selectUsers.executeQuery("select * from users");
//        while (result.next()) {
//            String login = result.getString("login");
//            String firstName = result.getString("first_name");
//            String lastName = result.getString("last_name");
//            System.out.println("[" + login + "] " + firstName + lastName);
//        }
//    }

//    public static String findByLogin(String loginUsers) throws SQLException {
//        Connection connection = DatabaseConnectionFactory.createConnection();
//        Statement selectUsers = connection.createStatement();
//        ResultSet result = selectUsers.executeQuery("select * from users where login = '" + loginUsers + "'");
//        AtomicReference<String> name = new AtomicReference<>();
//        while (result.next()) {
//            String login = result.getString("login");
//            String firstName = result.getString("first_name");
//            String lastName = result.getString("last_name");
//            System.out.println("[" + login + "] " + firstName + lastName);
//            name.set("Нашли пользователя -" + firstName + lastName);
//        }
//        return name.get();
//    }
//
//    public static void main(String[] args) throws SQLException, IOException {
//        createUser();
//    }

}
