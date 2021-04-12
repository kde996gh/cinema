package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private static final String INSERT_ROOM = "INSERT INTO ROOM (rowNumber, colNumber, name, seatNumber) VALUES (?,?,?,?)";
    private static final String UPDATE_ROOM = "UPDATE ROOM SET rowNumber=?, colNumber=?, name=?, seatNumber=? WHERE id=?";
    private static final String DELETE_SEAT_WITH_ID = "DELETE FROM SEAT WHERE room_id=?";
    private static final String SELECT_ONLY_NAMES = "SELECT name FROM ROOM";
    private String connectionURL;
    private static final String SELECT_ALL_ROOM = "SELECT * FROM ROOM";
    private static final String INSERT_SEATS = "INSERT INTO SEAT (room_id, seat_id, taken) VALUES (?,?,?)";
    private static final String DELETE_ROOM = "DELETE FROM ROOM WHERE id = ?";

    private Connection conn;

    private static RoomDAOImpl instance = new RoomDAOImpl();

    public static RoomDAOImpl getInstance() {
        return instance;
    }


    public RoomDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void fillSeats() {
      /*  try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(INSERT_SEATS);
               // PreparedStatement stmtDrop = conn.prepareStatement("DELETE FROM SEAT");
              // PreparedStatement stmtResetId = conn.prepareStatement("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='seat'")
        ){

          //  stmtDrop.executeUpdate();
          //  stmtResetId.executeUpdate();

            this.findAll().forEach(x -> {
                int a = x.getSeatNumber();
                for(int i=1; i<a+1; i++){
                    try {
                        stmt.setInt(3, i);
                        stmt.setInt(2, x.getId());
                        stmt.setInt(4, 1);
                      //  System.out.println("szek: " + i + "roomID : " + x.getId());
                        stmt.executeUpdate();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    @Override
    public List<Room> findAll() {

        List<Room> result = new ArrayList<>();

        try (
                //Connection conn = DriverManager.getConnection(connectionURL);
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
        System.out.println("RoomDAO save: name: " + room.getName() + ", row: " + room.getRowNumber() + ", id: " + room.getId());
        try (//Connection conn = DriverManager.getConnection(connectionURL);
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
        try (
                //Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(DELETE_ROOM);
        ) {
            stmt.setInt(1, room.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Room addRoomSeats(Room room) {


        try/*(    Connection conn = DriverManager.getConnection(connectionURL))*/ {
            PreparedStatement stmt;
            if (findRoomById(room)) {
                stmt = conn.prepareStatement(DELETE_SEAT_WITH_ID);
                stmt.setInt(1, room.getId());
                stmt.executeUpdate();
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < room.getSeatNumber() + 1; i++) {
                    stmt.setInt(1, room.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();
                }
                System.out.println("Megtaláltam!");
            } else {
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < room.getSeatNumber() + 1; i++) {
                    stmt.setInt(1, room.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();

                }
                System.out.println("Nem találtam meg a szobát!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return room;
    }

    @Override
    public boolean findRoomById(Room room) {
        List<Room> roomList = this.findAll();
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId() == (room.getId())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String findRoomNameById(int room_id) {
        String result = "";
        List<Room> roomList = this.findAll();
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId() == room_id) {
                result = roomList.get(i).getName();
            }
        }
        return result;
    }


    @Override
    public ObservableList<String> listByName() {
        ObservableList<String> result = FXCollections.observableArrayList();
        try (Statement stmt = conn.createStatement();
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
    public int getIdByRoomName(String newV) {
        int result = 0;
        List<Room> roomList = this.findAll();
        for (Room room : roomList) {
            if (room.getName().equals(newV))
                result = room.getId();
        }
        return result;
    }
}
