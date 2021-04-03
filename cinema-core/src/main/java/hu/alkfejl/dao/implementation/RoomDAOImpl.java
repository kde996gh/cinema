package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private  String connectionURL;
    private static final String SELECT_ALL_ROOM = "SELECT * FROM ROOM";


    public RoomDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");

    }

    @Override
    public List<Room> findAll() {

        List<Room> result = new ArrayList<>();

        try(
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_ROOM);
        ) {
                while(rs.next()){
                    Room current = new Room();
                    current.setId(rs.getInt("id"));
                    current.setRowNumber(rs.getInt("rowNumber"));
                    current.setRowSeats(rs.getInt("rowSeats"));
                    current.setName(rs.getString("name"));

                    result.add(current);
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public Room save(Room room) {
        return null;
    }

    @Override
    public void delete(Room room) {

    }
}
