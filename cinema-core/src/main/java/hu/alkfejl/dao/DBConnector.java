package hu.alkfejl.dao;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnector {
    //String dbURL = "jdbc:mysql://localhost:3306/mozi";
    //String username = "root";
    //String password ="";
//
    //Connection conn = null;
//
    //public Connection connect(){
    //    try {
    //        conn = DriverManager.getConnection(dbURL, username, password);
    //        System.out.println("SUCCESS IN CONNECTION");
    //        return conn;
//
    //    } catch (SQLException ex) {
    //        System.out.println("SQLException: " + ex.getMessage());
    //        System.out.println("SQLState: " + ex.getSQLState());
    //        System.out.println("VendorError: " + ex.getErrorCode());
    //        return null;
    //    }
    //}


    String dbURL = "jdbc:sqlite:C:/Users/emese/OneDrive/Asztali gép/SZTE IV/alkfejl/cinema/cinema-core/src/main/resources/mozi.db";
    private static Connection conn = null;

    public Connection connect(){
        try {
            if(conn == null) {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(dbURL);
            }
            return conn;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //to execute select queries
    public ResultSet selectQuery (String query) throws SQLException{
        Statement stm;
        try {
            stm = conn.createStatement();
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
            int affectedRows = pms.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
