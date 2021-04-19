package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM RESERVATION";
    private static final String INSERT_RESERVATION = "INSERT INTO RESERVATION(playtime_id, price, email, reserved_seat, price_sum) VALUES (?,?,?,?,?)";
    private static final String UPDATE_RESERVATION = "UPDATE RESERVATION SET playtime_id=?, price=?, email=?, reserved_seat=?, price_sum=? WHERE id=?";
    private static final String DELETE_WITH_ID_EMAIL = "DELETE FROM RESERVATION WHERE playtime_id=? AND email=?";

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
                currRes.setPlaytime_id(rs.getInt("playtime_id"));
                currRes.setPrice(rs.getInt("price"));
                currRes.setEmail(rs.getString("email"));
                currRes.setReserved_seat(rs.getString("reserved_seat"));
                currRes.setPrice_sum(rs.getInt("price_sum"));
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
                stmt.setInt(1, reservation.getPlaytime_id());
                stmt.setString(2, reservation.getEmail());
                stmt.executeUpdate();
            }
            stmt = conn.prepareStatement(INSERT_RESERVATION);
            stmt.setInt(1, reservation.getPlaytime_id());
            stmt.setInt(2, reservation.getPrice());
            stmt.setString(3, reservation.getEmail());
            stmt.setString(4, reservation.getReserved_seat());
            stmt.setInt(5, reservation.getPrice_sum());
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
            if (r.emailProperty().equals(res.emailProperty()) && r.getPlaytime_id() == res.getPlaytime_id()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfAlreadyBooked(String email, int ptid) {
        for (Reservation res : this.listReservations()) {
            if (res.getEmail().equals(email) && res.getPlaytime_id() == ptid) {
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


}
