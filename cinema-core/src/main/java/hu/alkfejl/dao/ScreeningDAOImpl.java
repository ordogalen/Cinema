package hu.alkfejl.dao;

import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;

import javax.xml.transform.Result;
import java.awt.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ScreeningDAOImpl implements ScreeningDAO{
    private final String DELETE_SCREENING ="DELETE FROM vetites WHERE vetites_id=%d";
    private final String INSERT_SCREENING = "INSERT INTO `vetites` (`vetites_id`, `film_nev`, `terem_nev`, `datum`, 'jegyar') VALUES (NULL, '%s', '%s', '%s %s', '%s')";
    Connection conn;
    DBConnector connection;



    public ScreeningDAOImpl(){
        connection = new DBConnector();
        conn = connection.connect();
    }


    @Override
    public Map<InputStream, String> ImageNameMap() {
        Map<InputStream, String> imageNameMap = new HashMap<InputStream, String>();
        try {
            ResultSet rs = connection.selectQuery("Select film_nev, boritokep from film");
            while(rs.next()){
                imageNameMap.put(rs.getBinaryStream("boritokep"),rs.getString("film_nev"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return imageNameMap;
    }

    @Override
    public List<String> HallNames() {
        List<String> hallNames = new ArrayList<>();
        try {
            ResultSet rs = connection.selectQuery("SELECT terem_nev FROM terem");
            while(rs.next()){
                hallNames.add(rs.getString("terem_nev"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hallNames;
    }

    @Override
    public List<String> ScreeningIDs() {
        List<String> screeningIds = new ArrayList<>();
        try {
            ResultSet rs = connection.selectQuery("SELECT vetites_id from vetites");
            while(rs.next()){
                screeningIds.add(rs.getString("vetites_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return screeningIds;
    }

    @Override
    public Screening getScreeingFromID(int value) {
        Screening temp = new Screening();
        try {
            ResultSet rs = connection.selectQuery("SELECT * FROM vetites WHERE vetites_id = "+value);
            rs.next();
            temp.setId(rs.getInt("vetites_id"));
            temp.setTerem_nev(rs.getString("terem_nev"));
            temp.setFilm_nev(rs.getString("film_nev"));
            temp.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
            temp.setJegyar(rs.getInt("jegyar"));
            //LEVÁGOM A MÁSODPERCEKET
            String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":"+
                    rs.getString("datum").split(" ")[1].split(":")[1];

            temp.setNap(nap);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }


    // TODO ELBASZTAM MEGKELL CSINÁLNI
    @Override
    public void delete(Screening s) {
        try {
            connection.executeQuery(String.format(DELETE_SCREENING,s.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void udpate(Screening s) {
        //TODO
    }

    @Override
    public void insert(Screening s) {
        try {
            connection.executeQuery(String.format(INSERT_SCREENING, s.getFilm_nev(), s.getTerem_nev(), s.getDatum().toString(), s.getNap(), s.getJegyar()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
