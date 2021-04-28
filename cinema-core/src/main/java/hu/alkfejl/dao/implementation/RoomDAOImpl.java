package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private static final String INSERT_ROOM = "INSERT INTO ROOM (rowNumber, colNumber, name, seatNumber) VALUES (?,?,?,?)";
    private static final String UPDATE_ROOM = "UPDATE ROOM SET rowNumber=?, colNumber=?, name=?, seatNumber=? WHERE id=?";
    private static final String SELECT_ONLY_NAMES = "SELECT name FROM ROOM";
    private static final String SELECT_ALL_ROOM = "SELECT * FROM ROOM";
    private static final String DELETE_ROOM = "DELETE FROM ROOM WHERE id = ?";

    private final String connectionURL = CinemaConfiguration.getValue("db.url");

    private static RoomDAOImpl instance;

    public static RoomDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new RoomDAOImpl();
        }
        return instance;
    }

    public RoomDAOImpl() {
    }

    @Override
    public List<Room> listRooms() {
        List<Room> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_ROOM);
        ) {
            while (rs.next()) {
                Room current = new Room();
                current.setId(rs.getInt("id"));
                current.setRowNumber(rs.getInt("rowNumber"));
                current.setColNumber(rs.getInt("colNumber"));
                current.setName(rs.getString("name"));
                current.setSeatNumber(rs.getInt("seatNumber"));

                result.add(current);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Room save(Room room) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = room.getId() <= 0 ? conn.prepareStatement(INSERT_ROOM, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_ROOM)
        ) {
            if (room.getId() > 0) {
                stmt.setInt(5, room.getId());
            }
            stmt.setInt(1, room.getRowNumber());
            stmt.setInt(2, room.getColNumber());
            stmt.setString(3, room.getName());
            stmt.setInt(4, room.getSeatNumber());
            stmt.executeUpdate();


            if (room.getId() <= 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    room.setId(rs.getInt(1));
                }
            }


        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return null;
        }


        return room;
    }

    @Override
    public void delete(Room room) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_ROOM)) {
            stmt.setInt(1, room.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ObservableList<String> listByName() {
        ObservableList<String> result = FXCollections.observableArrayList();
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ONLY_NAMES)
        ) {
            while (rs.next()) {
                String a = rs.getString("name");
                result.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Room getRoomByName(String name) {
        Room result = null;
        List<Room> roomList = this.listRooms();
        for (Room room : roomList) {
            if (room.getName().equals(name))
                result = room;
        }
        return result;
    }

}
