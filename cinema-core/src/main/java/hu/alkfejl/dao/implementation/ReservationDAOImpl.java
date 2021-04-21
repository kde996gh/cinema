package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM RESERVATION";
    private static final String INSERT_RESERVATION = "INSERT INTO RESERVATION(playtimeId, email, reservedSeat, priceSum, movieName, playtimeDate) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_WITH_ID_EMAIL = "DELETE FROM RESERVATION WHERE playtimeId=? AND email=?";

    private String connectionURL;
    private Connection conn;
    private static ReservationDAOImpl instance;

    public static ReservationDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new ReservationDAOImpl();
        }
        return instance;
    }

    public ReservationDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ObservableList<Reservation> listReservations() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_RESERVATIONS);
        ) {
            ObservableList<Reservation> ress = FXCollections.observableArrayList();

            while (rs.next()) {
                Reservation currRes = new Reservation();

                currRes.setId(rs.getInt("id"));
                currRes.setPlaytimeId(rs.getInt("playtimeId"));
                currRes.setEmail(rs.getString("email"));
                currRes.setReservedSeat(rs.getString("reservedSeat"));
                currRes.setPriceSum(rs.getInt("priceSum"));
                currRes.setPlaytimeDate(rs.getString("playtimeDate"));
                currRes.setMovieName(rs.getString("movieName"));
                ress.add(currRes);
            }
            return ress;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        try {
            PreparedStatement stmt;
            if (checkIfAlreadyExists(reservation)) {
                stmt = conn.prepareStatement(DELETE_WITH_ID_EMAIL);
                stmt.setInt(1, reservation.getPlaytimeId());
                stmt.setString(2, reservation.getEmail());
                stmt.executeUpdate();
            }
            stmt = conn.prepareStatement(INSERT_RESERVATION);
            stmt.setInt(1, reservation.getPlaytimeId());
            stmt.setString(2, reservation.getEmail());
            stmt.setString(3, reservation.getReservedSeat());
            stmt.setInt(4, reservation.getPriceSum());
            stmt.setString(5, reservation.getMovieName());
            stmt.setString(6, reservation.getPlaytimeDate());
            stmt.executeUpdate();

            if (reservation.getId() <= 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    reservation.setId(rs.getInt(1));
                }
            }


        } catch (SQLException s) {
            s.printStackTrace();
            return null;

        }
        return reservation;

    }

    @Override
    public boolean checkIfAlreadyExists(Reservation r) {
        for (Reservation res : this.listReservations()) {
            if (r.emailProperty().equals(res.emailProperty()) && r.getPlaytimeId() == res.getPlaytimeId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfAlreadyBooked(String email, int ptid) {
        for (Reservation res : this.listReservations()) {
            if (res.getEmail().equals(email) && res.getPlaytimeId() == ptid) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(String email) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : this.listReservations()) {
            if (reservation.getEmail().equals(email)) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public void deleteReservationByUser(String email, int intptid) {
        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(DELETE_WITH_ID_EMAIL);
            stmt.setInt(1, intptid);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a foglalás törlésekor!");
        }
    }

    @Override
    public Reservation getReservationByIdEmail(int ptid, String email) {
        for(Reservation r : this.listReservations()){
            if(r.getEmail().equals(email) && r.getPlaytimeId() == ptid){
                return r;
            }
        }
        return null;
    }


}
