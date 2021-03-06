package hu.alkfejl.dao;

import hu.alkfejl.model.Ticket;

import java.util.List;

public interface TicketDAO {
    List<Ticket> allTicket();
    void update(Ticket t);
    void delete(Ticket t);
    void add(Ticket t);
    int specificTicketID(int vetitesid, String helyek);

    List<Ticket> Search(String where, String what, String email);
    List<Ticket> specificTickets(int id);

}
