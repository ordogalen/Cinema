package hu.alkfejl.dao;

import hu.alkfejl.model.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO{

    Connection conn;
    DBConnector connection;

    public TicketDAOImpl(){
        connection = new DBConnector();
        conn = connection.connect();
    }

    @Override
    public List<Ticket> allTicket() {
        List<Ticket> temp = new ArrayList<>();
        try {
            ResultSet rs = connection.selectQuery("SELECT * from jegy");
            while(rs.next()){
                Ticket t = new Ticket();
                t.setEmail(rs.getString("email"));
                t.setJegy_id(rs.getInt("jegy_id"));
                t.setSzekek(rs.getString("szekek"));
                t.setVetites_id(rs.getInt("vetites_id"));
                t.setJegyar(rs.getInt("jegyar"));
                temp.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }

    @Override
    public void update(Ticket t) {

    }

    @Override
    public void delete(Ticket t) {
        try {
            connection.executeQuery("DELETE * from jegy WHERE jegy_id = '"+t.getJegy_id()+"'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Ticket t) {
        try {
            connection.executeQuery(String.format("INSERT INTO jegy(jegyar, vetites_id, email, szekek) VALUES ('%d','%d','%s','%s')", t.getJegyar(), t.getVetites_id(), t.getEmail(), t.getSzekek()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> specificTickets(int id) {
        try {
            List<Ticket> temp = new ArrayList<>();
            ResultSet rs = connection.selectQuery("SELECT * FROM jegy WHERE vetites_id="+id);
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
