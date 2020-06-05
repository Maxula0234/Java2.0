package org.levelup.lessons.lesson3;

import org.levelup.chat.jdbc.DatabaseConnectionFactory;

import java.sql.*;

public class jdbcApp {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnectionFactory.createConnection();
        //jdbc
        //Connection
        //Statement <- PreparedStatement <- CallableStatement
        //ResultSet

        //not SQL-injection
        PreparedStatement insertUser = connection.prepareStatement(
                "insert into users(login,first_name,last_name) values(?,?,?)"
        );

        insertUser.setString(1,"dMi");
        insertUser.setString(2,"Dmitry");
        insertUser.setString(3,"Oleth");

        int rowInsert = insertUser.executeUpdate();
        System.out.println("Change " + rowInsert + "string(s)");

        Statement selectUsers = connection.createStatement();

        //execut -> boolean true/false
        //executeQuery -> ResultSet - select query
        //executeUpdate -> int ->count change string insert/update/delete

        ResultSet result = selectUsers.executeQuery("select * from users");
        while (result.next()){
            int id = result.getInt(1);
            String login = result.getString("login");
            String firstName = result.getString(3);
            String lastName = result.getString("last_name");

            System.out.println(id + "|" + login + "|" + firstName + "|"+ lastName);
        }

        connection.close();
    }
}
