package hu.alkfejl.dao;
import hu.alkfejl.model.Hall;
import hu.alkfejl.model.Movie;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO{
    private final String DELETE_MOVIE = "DELETE FROM film WHERE film_nev = '%s'";
    private final String SELECT_MOVIE = "select * from film where film_nev='%s'";
    private final String UPDATE_MOVIE = "UPDATE film SET hossz = %s, korhatar=%s, rendezo='%s', szereplok='%s', leiras='%s' , boritokep='%s' WHERE film_nev= '%s'";
    private final String INSERT_MOVIE = "INSERT INTO film(film_nev,hossz,korhatar,rendezo,szereplok,leiras, boritokep) VALUES ('%s',%s,%s,'%s','%s','%s', '%s')";

    Connection conn;
    DBConnector connection;

    public MovieDAOImpl(){
        connection = new DBConnector();
        conn = connection.connect();
    }

    @Override
    public List<Movie> allMovie(){
        List<Movie> temp = new ArrayList<>();
        try {
            ResultSet rs = connection.selectQuery("SELECT * from film");
            while(rs.next()){
                Movie t = new Movie();
                t.setSzereplok(rs.getString("szereplok"));
                t.setRendezo(rs.getString("rendezo"));
                t.setKorhatar(rs.getString("korhatar"));
                t.setHossz(rs.getString("hossz"));
                t.setFilm_nev(rs.getString("film_nev"));
                t.setLeiras(rs.getString("leiras"));
                temp.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public int delete(Movie m) {
        try {
            connection.executeQuery(String.format(DELETE_MOVIE, m.getFilm_nev()));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int update(Movie m) {
        try {
            ResultSet rs = connection.selectQuery(String.format(SELECT_MOVIE, m.getFilm_nev()));
            rs.next();
            String hossz = m.getHossz().isEmpty() ? rs.getString("hossz") : m.getHossz();
            String kh = m.getKorhatar().isEmpty() ? rs.getString("korhatar") : m.getKorhatar();
            String rdz = m.getRendezo().isEmpty() ? rs.getString("rendezo") : m.getRendezo();
            String szrpl = m.getSzereplok().isEmpty() ? rs.getString("szereplok") : m.getSzereplok();
            String lrs = m.getLeiras().isEmpty() ? rs.getString("leiras") : m.getLeiras();
            String enc = m.getBoritokep() == null ? rs.getString("boritokep") : m.getBoritokep();
            connection.executeQuery(String.format(UPDATE_MOVIE, hossz, kh, rdz, szrpl, lrs, enc, m.getFilm_nev()));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int insert(Movie m) {
        if(!m.getFilm_nev().isEmpty() && !m.getHossz().isEmpty() && !m.getKorhatar().isEmpty() && !m.getLeiras().isEmpty() && !m.getBoritokep().isEmpty()){
            try {
                connection.executeQuery(String.format(INSERT_MOVIE, m.getFilm_nev(), m.getHossz(), m.getKorhatar(), m.getRendezo(), m.getSzereplok(), m.getLeiras(), m.getBoritokep()));
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
        try {
           ResultSet rs = connection.selectQuery(String.format("SELECT * FROM film WHERE %s LIKE '%s'", where, "%"+what+"%"));
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
