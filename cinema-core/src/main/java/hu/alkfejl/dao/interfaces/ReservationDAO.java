package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Reservation;
import javafx.collections.ObservableList;

public interface ReservationDAO {
    ObservableList<Reservation> listReservations();

    Reservation save(Reservation reservation);

    boolean checkIfAlreadyExists(Reservation r);

    boolean checkIfAlreadyBooked(String email, int ptid);
}
