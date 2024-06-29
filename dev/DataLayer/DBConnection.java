package DataLayer;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_NAME = "SuperLee.db";
    //private static final String USER = "your_username";
    //private static final String PASSWORD = "your_password";
    private static Connection connection;

    private DBConnection() {

    }
    static {
        connection = null;
        try {
            Path currentRelativePath = Paths.get("");
            String fullPath = currentRelativePath.toAbsolutePath().toString();
            //String url = "jdbc:sqlite:" + fullPath + "/" + DB_NAME;
            String url = "jdbc:sqlite:" + fullPath + File.separator + DB_NAME;
            connection = DriverManager.getConnection(url);
            //C:\Users\ירדן\IdeaProjects\ADSS_Group_AI\SuperLee.db
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
