package service.database;

import utils.properties.ConfigurationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBconnection {

    public static Connection getConnection() {

        Connection connection = null;
        try {

            Class.forName("org.sqlite.JDBC");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:"+ ConfigurationProperties.PROJECT_PATH + "/database/stackoverflow.db" );

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if(connection != null)
                connection.close();

        } catch(SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }
}
