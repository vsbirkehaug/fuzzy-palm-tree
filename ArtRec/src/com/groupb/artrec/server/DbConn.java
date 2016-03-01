package com.groupb.artrec.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Created by VSB on 23/02/2016.
 */
public class DbConn {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {

        if (connection != null && !connection.isClosed()) {
            return connection;
        } else {
            try {
                //can connect to localhost, replace localhost with the IP address of another machine
                String url = "jdbc:mysql://localhost:3306/artrec";
                String user = "root";
                String password = "root";

                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    protected static Connection connectToDatabase() {
        Connection conn = null;

        try {
            conn = DbConn.getConnection();
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

}
