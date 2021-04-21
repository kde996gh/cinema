package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Room;
import javafx.collections.ObservableList;

import java.util.List;

public interface RoomDAO {

    List<Room> listRooms();

    Room save(Room room);

    void delete(Room room);

    ObservableList<String> listByName();

    Room getRoomByName(String room_name);
}
