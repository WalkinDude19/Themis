//Still needs fixes
package appDatabase;

import java.sql.*;

public class DBConnection {

    private static final String databaseName = "U05bPD";
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + databaseName;
    private static final String username = "U05bPD";
    private static final String password = "53688457744";
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection conn;

    //establishes connection to the database 
    public static Connection makeConnection() throws ClassNotFoundException, SQLException, Exception {

        conn = DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection successful.");
        return conn;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, username, password);
        return connection;
    }

    //closes database connection
    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection closed.");
    }
}
