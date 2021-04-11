package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Ticket;

import java.util.List;

public interface TicketDAO {

    List<Ticket> findAllTicket();
    Ticket save(Ticket ticket);
    void delete(Ticket ticket);
}
