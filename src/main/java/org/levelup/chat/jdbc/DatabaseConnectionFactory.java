package org.levelup.chat.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {

    //JDBC - Java Database Connectivity
    //API для подключения к базе
    // Connection

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Connection
    public static Connection createConnection() throws SQLException {
        //url: jdbc:<vendor_name>://<host>:<port>/<databese_name>
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/chat",
                "postgres",
                "кщще"
        );
    }
}
