package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Room;
import javafx.collections.ObservableList;

import java.util.List;

public interface RoomDAO {

    List<Room> findAll();

    Room save(Room room);

    void delete(Room room);

    void fillSeats();

    Room addRoomSeats(Room room);

    boolean findRoomById(Room room);

    String findRoomNameById(int room_id);

    ObservableList<String> listByName();

    int getIdByRoomName(String newV);

    Room getRoomByName(String room_name);
}
