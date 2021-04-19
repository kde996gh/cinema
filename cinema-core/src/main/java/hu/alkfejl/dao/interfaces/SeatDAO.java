package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Seat;
import javafx.collections.ObservableList;

public interface SeatDAO {

    void reserve(int roomId, int seatId); //helyet lefoglaló metódus
    public ObservableList<Seat> getAllSeats();


    ObservableList<Seat> getPlayTimeSeats(int ptId);

    void updateOnDelete(int ptid, int i);
}
