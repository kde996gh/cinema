package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Reservation;
import javafx.collections.ObservableList;

import java.util.List;

public interface ReservationDAO {
    ObservableList<Reservation> listReservations();

    Reservation save(Reservation reservation);

    boolean checkIfAlreadyExists(Reservation r);

    boolean checkIfAlreadyBooked(String email, int ptid);

    List<Reservation> getUserReservations(String email);

    void deleteReservationByUser(String email, int intptid);

    Reservation getReservationByIdEmail(int ptid, String email);
}
