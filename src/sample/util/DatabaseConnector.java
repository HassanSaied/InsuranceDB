package sample.util;

/**
 * Created by hassan on 6/29/17.
 */

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class DatabaseConnector {

    private  static Connection databaseConnection = null;
    private String userName = "root";
    private String passWord = "AssAssin_108";
    private String connectionURL = "jdbc:mysql://localhost/InsuranceDB?useSSL=false";
    private static MysqlDataSource dataSource = new MysqlDataSource();
    private DatabaseConnector()
    {
        dataSource.setURL(connectionURL);
        dataSource.setUser(userName);
        dataSource.setPassword(passWord);

        try{
            databaseConnection = dataSource.getConnection();

        }
        catch (SQLException exception) {
            System.out.println("Can't get Connection Excpetion " + exception.getMessage());
            System.out.println("SQL State: " + exception.getSQLState());
        }
    }

    public static Connection getDatabaseConnection() {
        if(databaseConnection == null)
            return new DatabaseConnector().databaseConnection;
        else return databaseConnection;

    }
}
