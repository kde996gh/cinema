package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.SeatDAO;
import hu.alkfejl.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOImpl implements SeatDAO {

    private static final String SELECT_ALL_SEAT = "SELECT * FROM SEAT";
    private static final String UPDATE_RESERVE = "UPDATE SEAT SET taken=1 WHERE playtimeId=? AND seatId=?";
    private static final String UPDATE_ON_DELETE = "UPDATE SEAT SET taken=0 WHERE playtimeId=? AND seatId=?";
    private static final String DELETE_SEATS = "DELETE FROM SEAT WHERE playtimeId=?";
    private String connectionURL = CinemaConfiguration.getValue("db.url");
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
    }

    @Override
    public void reserve(int playtimeId, int seatId) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(UPDATE_RESERVE)
        ) {
            stmt.setInt(1, playtimeId);
            stmt.setInt(2, seatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba szék updatekor");
        }
    }

    @Override
    public List<Seat> getAllSeats() {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_SEAT);
        ) {
            List<Seat> seats = new ArrayList<>();

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
    public List<Seat> getPlayTimeSeats(int ptId) {
        List<Seat> sts = this.getAllSeats();
        List<Seat> result = new ArrayList<>();
        for (Seat s : sts) {
            if (s.getPlaytimeId() == ptId)
                result.add(s);
        }
        return result;
    }

    @Override
    public void updateOnDelete(int ptid, int seatid) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(UPDATE_ON_DELETE)) {
            stmt.setInt(1, ptid);
            stmt.setInt(2, seatid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba szék updatekor");
        }
    }

    @Override
    public void deleteSeatsByPlayTimeId(int ptid) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(DELETE_SEATS)) {
            stmt.setInt(1, ptid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba szék updatekor");
        }
    }

}
