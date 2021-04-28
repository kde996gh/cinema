package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Seat;
import javafx.collections.ObservableList;

import java.util.List;

public interface SeatDAO {

    void reserve(int roomId, int seatId); //helyet lefoglaló metódus

    List<Seat> getAllSeats();

    List<Seat> getPlayTimeSeats(int ptId);

    void updateOnDelete(int ptid, int i);

    void deleteSeatsByPlayTimeId(int ptid);
}
