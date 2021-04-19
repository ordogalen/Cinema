package hu.alkfejl.dao;

import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Ticket;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketDAOImpl implements TicketDAO{
    private final String ADDTICKET = "INSERT INTO jegy(jegyar, vetites_id, email, szekek, datum) VALUES (?,?,?,?,?)";
    private final String UPDATETICKET = "UPDATE jegy SET jegyar = ?, szekek= ? WHERE jegy_id = ?";
    private final String DELETETICKET = "DELETE from jegy WHERE jegy_id = ?";


    private String dbURL;
    private final Properties properties = new Properties();

    public TicketDAOImpl(){
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            dbURL = properties.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> allTicket() {
        List<Ticket> temp = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)){
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from jegy");
            while(rs.next()){
                Ticket t = new Ticket();
                t.setEmail(rs.getString("email"));
                t.setJegy_id(rs.getInt("jegy_id"));
                t.setSzekek(rs.getString("szekek"));
                t.setVetites_id(rs.getInt("vetites_id"));
                t.setJegyar(rs.getInt("jegyar"));
                t.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
                //LEVÁGOM A MÁSODPERCEKET
                String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":"+
                        rs.getString("datum").split(" ")[1].split(":")[1];
                t.setNap(nap);
                temp.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }

    @Override
    public void update(Ticket t) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(UPDATETICKET);
            pstm.setInt(1,t.getJegyar());
            pstm.setString(2,t.getSzekek());
            pstm.setInt(3,t.getJegy_id());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Ticket t) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(DELETETICKET);
            pstm.setInt(1,t.getJegy_id());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Ticket t) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement(ADDTICKET);
            pstm.setInt(1,t.getJegyar());
            pstm.setInt(2,t.getVetites_id());
            pstm.setString(3,t.getEmail());
            pstm.setString(4,t.getSzekek());
            pstm.setString(5,t.getDatum().toString()+" "+t.getNap());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int specificTicketID(int vetitesid, String helyek) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT jegy_id from jegy WHERE vetites_id = ? and szekek = ?");
            pstm.setInt(1,vetitesid);
            pstm.setString(2,helyek);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getInt("jegy_id");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Ticket> Search(String where, String what, String email) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(dbURL)) {
            PreparedStatement pstm = c.prepareStatement("SELECT * FROM jegy WHERE " + where + " LIKE '%" + what + "%' AND email = ?");
            pstm.setString(1,email);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setEmail(rs.getString("email"));
                t.setJegy_id(rs.getInt("jegy_id"));
                t.setSzekek(rs.getString("szekek"));
                t.setVetites_id(rs.getInt("vetites_id"));
                t.setJegyar(rs.getInt("jegyar"));
                t.setDatum(LocalDate.parse(rs.getString("datum").split(" ")[0]));
                String nap = rs.getString("datum").split(" ")[1].split(":")[0] + ":" +
                        rs.getString("datum").split(" ")[1].split(":")[1];
                t.setNap(nap);
                tickets.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> specificTickets(int id) {
        try (Connection c = DriverManager.getConnection(dbURL)){

            PreparedStatement pstm = c.prepareStatement("SELECT * FROM jegy WHERE vetites_id=?");
            pstm.setInt(1,id);
            List<Ticket> temp = new ArrayList<>();
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                Ticket t = new Ticket();
                t.setJegyar(rs.getInt("jegyar"));
                t.setSzekek(rs.getString("szekek"));
                t.setVetites_id(rs.getInt("vetites_id"));
                t.setEmail(rs.getString("email"));
                t.setJegy_id(rs.getInt("jegy_id"));
                temp.add(t);
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
