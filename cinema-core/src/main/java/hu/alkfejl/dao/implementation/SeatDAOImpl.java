package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.SeatDAO;
import hu.alkfejl.model.Seat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SeatDAOImpl implements SeatDAO {

    private static final String SELECT_ALL_SEAT = "SELECT * FROM SEAT";
    private static final String UPDATE_RESERVE = "UPDATE SEAT SET taken=1 WHERE playtimeId=? AND seatId=?";
    private static final String UPDATE_ON_DELETE = "UPDATE SEAT SET taken=0 WHERE playtimeId=? AND seatId=?";
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
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_RESERVE)) {
            stmt.setInt(1, playtimeId);
            stmt.setInt(2, seatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
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
                seat.setPlaytimeId(rs.getInt("playtimeId"));
                seat.setSeatId(rs.getInt("seatId"));
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
    public ObservableList<Seat> getPlayTimeSeats(int ptId) {
        ObservableList<Seat> result = FXCollections.observableArrayList();
        ObservableList<Seat> sts = this.getAllSeats();
        for (Seat s : sts) {
            if (s.getPlaytimeId() == ptId)
                result.add(s);
        }
        return result;
    }

    @Override
    public void updateOnDelete(int ptid, int seatid) {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_ON_DELETE)) {
            stmt.setInt(1, ptid);
            stmt.setInt(2, seatid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba szék updatekor");
        }
    }

}
