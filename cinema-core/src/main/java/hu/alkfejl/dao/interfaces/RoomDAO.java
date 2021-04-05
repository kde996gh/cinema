package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Room;

import java.util.List;

public interface RoomDAO {

    List<Room> findAll();
    Room save(Room room);
    void delete(Room room);
    void fillSeats();
}
