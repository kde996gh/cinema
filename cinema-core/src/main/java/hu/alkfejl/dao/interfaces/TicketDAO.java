package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Ticket;
import javafx.collections.ObservableList;

import java.util.List;

public interface TicketDAO {

    List<Ticket> findAllTicket();
    Ticket save(Ticket ticket);
    void delete(Ticket ticket);
    ObservableList<Integer> findTicketTypes();
    public Integer findTicketTypeById(int room_id);

    int getIdByTicketType(Integer newV);

    Ticket getTicketByType(int ticket_type);
}
