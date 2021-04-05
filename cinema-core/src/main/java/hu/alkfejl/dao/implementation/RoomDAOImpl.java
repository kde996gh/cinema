package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private static final String INSERT_ROOM = "INSERT INTO ROOM (rowNumber, colNumber, name) VALUES (?,?,?)";
    private static final String UPDATE_ROOM = "UPDATE ROOM SET rowNumber=?, colNumber=?, name=? WHERE id=?";
    private  String connectionURL;
    private static final String SELECT_ALL_ROOM = "SELECT * FROM ROOM";
    private static final String INSERT_INTO_SEATS  = "INSERT INTO SEAT (id, room_id, seat_id) VALUES (?,?,?)";
    private static final String DELETE_ROOM  = "DELETE FROM ROOM WHERE id = ?";


    public RoomDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");

    }
    @Override
    public void fillSeats(){
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(INSERT_INTO_SEATS);
                PreparedStatement stmtDrop = conn.prepareStatement("DELETE FROM SEAT");
               PreparedStatement stmtResetId = conn.prepareStatement("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='seat'")
        ){

            stmtDrop.executeUpdate();
            stmtResetId.executeUpdate();
            this.findAll().forEach(x -> {
                int a = x.getColNumber() * x.getRowNumber();
                for(int i=1; i<a+1; i++){
                    try {
                        stmt.setInt(3, i);
                        stmt.setInt(2, x.getId());
                      //  System.out.println("szek: " + i + "roomID : " + x.getId());
                        stmt.executeUpdate();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                    current.setColNumber(rs.getInt("colNumber"));
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
        System.out.println("RoomDAO save: name: " + room.getName() + ", row: " + room.getRowNumber() + ", id: " + room.getId());
    try(Connection conn = DriverManager.getConnection(connectionURL);
        PreparedStatement stmt = room.getId() <= 0 ? conn.prepareStatement(INSERT_ROOM, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_ROOM)

    ){
        if(room.getId() > 0) {
            stmt.setInt(4, room.getId());
        }
        stmt.setInt(1, room.getRowNumber());
        stmt.setInt(2, room.getColNumber());
        stmt.setString(3, room.getName());
        stmt.executeUpdate();


        if(room.getId() <= 0){
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                room.setId(rs.getInt(1));
            }
        }




    }catch(SQLException sqe){
        sqe.printStackTrace();
        return null;
    }


        return room;
    }

    @Override
    public void delete(Room room) {

        try(
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(DELETE_ROOM);

        ) {
            stmt.setInt(1, room.getId());
            stmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
