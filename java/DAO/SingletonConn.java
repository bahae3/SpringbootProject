package DAO;

import java.sql.*;

public class SingletonConn {
    private static Connection connection;

    static {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp7awd", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
