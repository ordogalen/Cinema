package hu.alkfejl.dao;
import hu.alkfejl.model.Hall;
import hu.alkfejl.model.Movie;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MovieDAOImpl implements MovieDAO{
    private final String DELETE_MOVIE = "DELETE FROM film WHERE film_nev = ?";
    private final String SELECT_MOVIE = "select * from film where film_nev=?";
    private final String UPDATE_MOVIE = "UPDATE film SET hossz = ?, korhatar=?, rendezo=?, szereplok=?, leiras=? , boritokep=? WHERE film_nev= ?";
    private final String INSERT_MOVIE = "INSERT INTO film(film_nev,hossz,korhatar,rendezo,szereplok,leiras, boritokep) VALUES (?,?,?,?,?,?,?)";

    private String dbURL;
    private final Properties properties = new Properties();


    public MovieDAOImpl(){
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            dbURL = properties.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> allMovie(){
        List<Movie> temp = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select * from film");
            while(rs.next()){
                Movie t = new Movie();
                t.setSzereplok(rs.getString("szereplok"));
                t.setRendezo(rs.getString("rendezo"));
                t.setKorhatar(rs.getString("korhatar"));
                t.setHossz(rs.getString("hossz"));
                t.setFilm_nev(rs.getString("film_nev"));
                t.setLeiras(rs.getString("leiras"));
                t.setBoritokep(rs.getString("boritokep"));
                temp.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Movie specificMovie(String movie_name) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * FROM film WHERE film_nev = ?");
            pstm.setString(1,movie_name);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            Movie t = new Movie();
            t.setSzereplok(rs.getString("szereplok"));
            t.setRendezo(rs.getString("rendezo"));
            t.setKorhatar(rs.getString("korhatar"));
            t.setHossz(rs.getString("hossz"));
            t.setFilm_nev(rs.getString("film_nev"));
            t.setLeiras(rs.getString("leiras"));
            return t;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public int delete(Movie m) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(DELETE_MOVIE);
            pstm.setString(1,m.getFilm_nev());
            pstm.executeUpdate();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int update(Movie m) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(SELECT_MOVIE);
            pstm.setString(1,m.getFilm_nev());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                String hossz = m.getHossz().isEmpty() ? rs.getString("hossz") : m.getHossz();
                String kh = m.getKorhatar().isEmpty() ? rs.getString("korhatar") : m.getKorhatar();
                String rdz = m.getRendezo().isEmpty() ? rs.getString("rendezo") : m.getRendezo();
                String szrpl = m.getSzereplok().isEmpty() ? rs.getString("szereplok") : m.getSzereplok();
                String lrs = m.getLeiras().isEmpty() ? rs.getString("leiras") : m.getLeiras();
                String enc = m.getBoritokep() == null ? rs.getString("boritokep") : m.getBoritokep();
                pstm = c.prepareStatement(UPDATE_MOVIE);
                pstm.setString(1,hossz);
                pstm.setString(2,kh);
                pstm.setString(3,rdz);
                pstm.setString(4,szrpl);
                pstm.setString(5,lrs);
                pstm.setString(6,enc);
                pstm.setString(7,m.getFilm_nev());
                pstm.executeUpdate();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int insert(Movie m) {
        System.out.println(m.getBoritokep().isEmpty());
        if(!m.getFilm_nev().isEmpty() && !m.getHossz().isEmpty() && !m.getKorhatar().isEmpty() && !m.getLeiras().isEmpty() && !m.getBoritokep().isEmpty()){
            try (Connection c = DriverManager.getConnection(dbURL)){
                PreparedStatement pstm = c.prepareStatement(INSERT_MOVIE);
                pstm.setString(1,m.getFilm_nev());
                pstm.setString(2,m.getHossz());
                pstm.setString(3,m.getKorhatar());
                pstm.setString(4,m.getRendezo());
                pstm.setString(5,m.getSzereplok());
                pstm.setString(6,m.getLeiras());
                pstm.setString(7,m.getBoritokep());
                pstm.executeUpdate();
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }else{
            return 2;
        }
    }
    
    public List<Movie> search(String where, String what){
        List<Movie> movies = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * FROM film WHERE "+where+" LIKE '%"+what+"%'");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                   Movie temp = new Movie();
                   temp.setFilm_nev(rs.getString("film_nev"));
                   temp.setHossz(rs.getString("hossz"));
                   temp.setLeiras(rs.getString("leiras"));
                   temp.setKorhatar(rs.getString("korhatar"));
                   temp.setRendezo(rs.getString("rendezo"));
                   temp.setSzereplok(rs.getString("szereplok"));
                   temp.setBoritokep(rs.getString("boritokep"));
                   movies.add(temp);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
