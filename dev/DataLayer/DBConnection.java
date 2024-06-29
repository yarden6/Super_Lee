package DataLayer;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //private static final String DB_NAME = "SuperLee.db";
    //private static final String USER = "your_username";
    //private static final String PASSWORD = "your_password";
    private static Connection connection;

    private DBConnection() {

    }

    public static void connect(String DB_NAME) {
        try {
            Path currentRelativePath = Paths.get("");
            String fullPath = currentRelativePath.toAbsolutePath().toString();
            String url = "jdbc:sqlite:" + fullPath + File.separator + DB_NAME;
           connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }



    public static Connection getConnection() {
        return connection;
    }
}
