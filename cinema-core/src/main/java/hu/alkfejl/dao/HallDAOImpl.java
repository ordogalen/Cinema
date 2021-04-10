package hu.alkfejl.dao;

import hu.alkfejl.model.Hall;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallDAOImpl implements HallDAO{
    private final String SELECT_ALL_HALLS = "SELECT * FROM terem";
    private final String INSERT_HALL = "INSERT INTO terem(terem_nev, sor, oszlop) VALUES ('%s', %d, %d)";
    private final String DELETE_HALL = "DELETE FROM terem WHERE terem_nev = '%s'";
    private final String UPDATE_HALL = "UPDATE terem SET terem_nev = '%s' WHERE terem_nev = '%s'";

    Connection conn;
    DBConnector connection;

    public HallDAOImpl(){
        connection = new DBConnector();
        conn = connection.connect();
    }

    @Override
    public List<Hall> allHall() {
        List<Hall> HallList = new ArrayList<>();
        try {
            ResultSet rs = connection.selectQuery(SELECT_ALL_HALLS);
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
        try {
            Hall temp = new Hall();
            ResultSet rs = connection.selectQuery(String.format("Select * from terem WHERE terem_nev='%s'", name));
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
        try {
            connection.executeQuery(String.format(DELETE_HALL, h.getTerem_nev()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void udpate(Hall h, String lastname) {
        try {
            if(h.getTerem_nev().length()>3) {
                connection.executeQuery(String.format(UPDATE_HALL, h.getTerem_nev(), lastname));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Hall h) {
        try {
            if(h.getTerem_nev().length()>3)
                connection.executeQuery(String.format(INSERT_HALL, h.getTerem_nev(), h.getSor(), h.getOszlop()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
