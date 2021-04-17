package hu.alkfejl.dao;

import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;

import javax.xml.transform.Result;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ScreeningDAOImpl implements ScreeningDAO{
    private final String DELETE_SCREENING ="DELETE FROM vetites WHERE vetites_id=?";
    private final String INSERT_SCREENING = "INSERT INTO `vetites` (`vetites_id`, `film_nev`, `terem_nev`, `datum`, 'jegyar') VALUES (NULL, ?, ?, ?, ?)";


    private String dbURL;
    private final Properties properties = new Properties();


    public ScreeningDAOImpl(){
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            dbURL = properties.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<InputStream, String> ImageNameMap() {
        Map<InputStream, String> imageNameMap = new HashMap<InputStream, String>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("Select film_nev, boritokep from film");
            while(rs.next()){
                imageNameMap.put(rs.getBinaryStream("boritokep"),rs.getString("film_nev"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return imageNameMap;
    }

    @Override
    public List<Screening> allScreening() {
        List<Screening> screenings = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vetites");
            while(rs.next()){
                Screening temp = new Screening();
                temp.setId(rs.getInt("vetites_id"));
                temp.setTerem_nev(rs.getString("terem_nev"));
                temp.setFilm_nev(rs.getString("film_nev"));
                temp.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
                temp.setJegyar(rs.getInt("jegyar"));
                String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":"+
                        rs.getString("datum").split(" ")[1].split(":")[1];
                temp.setNap(nap);
                System.out.println(temp.getDatum());
                screenings.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return screenings;
    }

    @Override
    public List<String> HallNames() {
        List<String> hallNames = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT terem_nev FROM terem");
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
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT vetites_id from vetites");
            while(rs.next()){
                screeningIds.add(rs.getString("vetites_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return screeningIds;
    }

    @Override
    public List<Screening> movieScreening(String movieName) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * FROM vetites WHERE film_nev = ?");
            pstm.setString(1,movieName);
            ResultSet rs = pstm.executeQuery();
            List<Screening> screenings = new ArrayList<>();
            while(rs.next()){
                Screening temp = new Screening();
                temp.setId(rs.getInt("vetites_id"));
                temp.setTerem_nev(rs.getString("terem_nev"));
                temp.setFilm_nev(rs.getString("film_nev"));
                temp.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
                temp.setJegyar(rs.getInt("jegyar"));
                String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":"+
                        rs.getString("datum").split(" ")[1].split(":")[1];
                temp.setNap(nap);
                screenings.add(temp);
            }
            return screenings;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Screening specificScreening(int id) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * from vetites where vetites_id = ?");
            pstm.setInt(1,id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                Screening temp = new Screening();
                temp.setId(rs.getInt("vetites_id"));
                temp.setTerem_nev(rs.getString("terem_nev"));
                temp.setFilm_nev(rs.getString("film_nev"));
                temp.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
                temp.setJegyar(rs.getInt("jegyar"));
                String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":"+
                        rs.getString("datum").split(" ")[1].split(":")[1];
                temp.setNap(nap);
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Screening getScreeingFromID(int value) {
        Screening temp = new Screening();
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * from vetites where vetites_id = ?");
            pstm.setInt(1,value);
            ResultSet rs = pstm.executeQuery();

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


    @Override
    public void delete(Screening s) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(DELETE_SCREENING);
            pstm.setInt(1,s.getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insert(Screening s) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(INSERT_SCREENING);
            pstm.setString(1,s.getFilm_nev());
            pstm.setString(2,s.getTerem_nev());
            pstm.setString(3, s.getDatum().toString()+" "+s.getNap());
            pstm.setInt(4,s.getJegyar());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
