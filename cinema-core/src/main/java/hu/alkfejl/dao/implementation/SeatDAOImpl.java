package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.SeatDAO;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Seat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOImpl implements SeatDAO {

    private static final String SELECT_ALL_SEAT = "SELECT * FROM SEAT";
    private static final String UPDATE_RESERVE = "UPDATE SEAT SET taken=1 WHERE playtime_id=? AND seat_id=?";
    private String connectionURL;
    private Connection conn;
    private static SeatDAOImpl instance;

    public static SeatDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new SeatDAOImpl();
        }
        return instance;
    }

    public SeatDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {

            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void reserve(int playtimeId, int seatId) {
        //    private static final String UPDATE_RESERVE =
        //    "UPDATE SEAT SET taken=1 WHERE playtime_id=? AND seat_id=?";
        try(PreparedStatement stmt = conn.prepareStatement(UPDATE_RESERVE)){
            stmt.setInt(1, playtimeId);
            stmt.setInt(2, seatId);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Hiba szék updatekor");
        }

    }

    @Override
    public ObservableList<Seat> getAllSeats() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SEAT);
        ) {
            ObservableList<Seat> seats = FXCollections.observableArrayList();

            while (rs.next()) {
                Seat seat = new Seat();

                seat.setId(rs.getInt("id"));
                seat.setPlaytime_id(rs.getInt("playtime_id"));
                seat.setSeat_id(rs.getInt("seat_id"));
                seat.setTaken(rs.getInt("taken"));

                seats.add(seat);
            }
            return seats;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    @Override
    public ObservableList<Seat> getPlayTimeSeats(int ptId){
        ObservableList<Seat> result = FXCollections.observableArrayList();
        ObservableList<Seat> sts = this.getAllSeats();
        for(Seat s : sts){
            if(s.getPlaytime_id() == ptId){
                result.add(s);
            }
        }
        return result;
    }

}
