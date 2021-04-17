package hu.alkfejl.dao;

import hu.alkfejl.model.Hall;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HallDAOImpl implements HallDAO{
    private final String SELECT_ALL_HALLS = "SELECT * FROM terem";
    private final String INSERT_HALL = "INSERT INTO terem(terem_nev, sor, oszlop) VALUES (?,?,?)";
    private final String DELETE_HALL = "DELETE from terem WHERE terem_nev = ?";

    private final String UPDATE_HALL = "UPDATE terem SET terem_nev = ? WHERE terem_nev = ?";


    private String dbURL;
    private final Properties properties = new Properties();

    public HallDAOImpl(){
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            dbURL = properties.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    // TODO EZ DONE
    @Override
    public List<Hall> allHall() {
        List<Hall> HallList = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL_HALLS);
            while(rs.next()){
                Hall h = new Hall();
                h.setTerem_nev(rs.getString("terem_nev"));
                h.setSor(rs.getInt("sor"));
                h.setOszlop(rs.getInt("oszlop"));
                HallList.add(h);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return HallList;
    }

    @Override
    public Hall specificHall(String name) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            Hall temp = new Hall();
            PreparedStatement pstm = c.prepareStatement("Select * from terem WHERE terem_nev=?");
            pstm.setString(1,name);
            ResultSet rs = pstm.executeQuery();

            rs.next();
            temp.setTerem_nev(rs.getString("terem_nev"));
            temp.setOszlop(rs.getInt("oszlop"));
            temp.setSor(rs.getInt("sor"));

            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void delete(Hall h){
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(DELETE_HALL);
            pstm.setString(1,h.getTerem_nev());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO EZ DONE
    @Override
    public void udpate(Hall h, String lastname) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            if(h.getTerem_nev().length()>3) {
                PreparedStatement pstm = c.prepareStatement(UPDATE_HALL);
                pstm.setString(1,h.getTerem_nev());
                pstm.setString(2,lastname);
                pstm.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Hall h) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(INSERT_HALL);
            if(h.getTerem_nev().length()>3)
                pstm.setString(1,h.getTerem_nev());
                pstm.setInt(2,h.getSor());
                pstm.setInt(3, h.getOszlop());
                pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
