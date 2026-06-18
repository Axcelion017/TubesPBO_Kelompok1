package tubespbo.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@100.114.119.105:1521/XEPDB1";
    private static final String USERNAME = "TUBESPBO";
    private static final String PASSWORD = "TubesPBO19";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}