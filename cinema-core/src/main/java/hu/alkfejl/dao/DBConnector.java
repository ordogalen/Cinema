package hu.alkfejl.dao;

import org.sqlite.SQLiteConfig;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnector {

    private static Connection conn = null;

    private final Properties properties = new Properties();

    public Connection connect(){
        try {
            if(conn == null) {
                properties.load(getClass().getResourceAsStream("/application.properties"));
                String dbURL = properties.getProperty("db.url");
                Class.forName("org.sqlite.JDBC");
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                conn = DriverManager.getConnection(dbURL, config.toProperties());
            }
            return conn;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //to execute select queries
    public ResultSet selectQuery (String query) throws SQLException{
        try {
            Statement stm = conn.createStatement();
            return stm.executeQuery(query);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //executeQuery waits for a query and it will be executed in the database (insert, delete, update)
    //todo kezden ivalamit azzal hogy visszaadja mennyi sort updatelt
    public void executeQuery(String query) throws Exception {
        try {
            PreparedStatement pms = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pms.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
