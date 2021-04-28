package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Room;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlayTimeDAOImpl implements PlayTimeDAO {

    private static final String SELECT_ALL_PLAYTIME = "SELECT * FROM PLAYTIME";
    private static final String INSERT_PLAYTIME = "INSERT INTO PLAYTIME (roomName, movieName, ticketType, playTimeDate, playTimeHours) values (?,?,?,?,?)";
    private static final String UPDATE_PLAYTIME = "UPDATE PLAYTIME SET roomName=?, movieName=?, ticketType=?, playTimeDate=?, playTimeHours=? WHERE id=?";
    private static final String DELETE_PLAYTIME = "DELETE FROM PLAYTIME WHERE id=?";
    private static final String DELETE_SEAT_WITH_ID = "DELETE FROM SEAT WHERE playtimeId=?";
    private static final String INSERT_SEATS = "INSERT INTO SEAT (playtimeId, seatId, taken) VALUES (?,?,?)";

    private final String connectionURL = CinemaConfiguration.getValue("db.url");

    private static PlayTimeDAOImpl instance;

    public static PlayTimeDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new PlayTimeDAOImpl();
        }
        return instance;
    }

    public PlayTimeDAOImpl() {
    }

    @Override
    public List<PlayTime> listPlayTimes() {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_PLAYTIME);
        ) {
            List<PlayTime> playtimes = new ArrayList<>();
            while (rs.next()) {
                PlayTime pt = new PlayTime();

                pt.setId(rs.getInt("id"));
                pt.setRoomName(rs.getString("roomName"));
                pt.setMovieName(rs.getString("movieName"));
                pt.setTicketType(rs.getInt("ticketType"));

                Date date = Date.valueOf(rs.getString("playTimeDate"));
                pt.setPlayTimeDate(date == null ? LocalDate.now() : date.toLocalDate());

                pt.setPlayTimeHours(rs.getString("playTimeHours"));


                playtimes.add(pt);
            }
            return playtimes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public PlayTime save(PlayTime playTime) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = playTime.getId() <= 0 ? conn.prepareStatement(INSERT_PLAYTIME, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_PLAYTIME)
        ) {
            if (playTime.getId() > 0) {
                stmt.setInt(6, playTime.getId());
            }
            stmt.setString(1, playTime.getRoomName());
            stmt.setString(2, playTime.getMovieName());
            stmt.setInt(3, playTime.getTicketType());
            stmt.setString(4, playTime.getPlayTimeDate().toString());
            stmt.setString(5, playTime.getPlayTimeHours());
            stmt.executeUpdate();

            if (playTime.getId() <= 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    playTime.setId(rs.getInt(1));
                }
            }

        } catch (SQLException s) {
            s.printStackTrace();
            return null;

        }
        return playTime;
    }

    @Override
    public void delete(PlayTime playTime) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(DELETE_PLAYTIME)) {
            stmt.setInt(1, playTime.getId());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<PlayTime> getMoviePlayTimes(String movieName) {
        List<PlayTime> result = new ArrayList<>();
        List<PlayTime> pts = this.listPlayTimes();
        for (PlayTime ptit : pts) {
            if (ptit.getMovieName().equals(movieName)) {
                result.add(ptit);
            }
        }
        return result;
    }

    @Override
    public void addRoomSeats(PlayTime playTime) {
        try (Connection conn = DriverManager.getConnection(connectionURL);) {
            PreparedStatement stmt;
            Room r = RoomDAOImpl.getInstance().getRoomByName(playTime.getRoomName());

            if (findPlayTimeById(playTime)) {
                stmt = conn.prepareStatement(DELETE_SEAT_WITH_ID);
                stmt.setInt(1, playTime.getId());
                stmt.executeUpdate();
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < r.getSeatNumber() + 1; i++) {
                    stmt.setInt(1, playTime.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();
                }
                stmt.close();
            } else {
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < r.getSeatNumber() + 1; i++) {
                    stmt.setInt(1, playTime.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();
                }
                stmt.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteRoomSeat(PlayTime pt) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_SEAT_WITH_ID)) {
            stmt.setInt(1, pt.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a szék törlésekor!");
        }
    }

    @Override
    public PlayTime getPlayTimeById(int playtimeid) {
        for (PlayTime pt : this.listPlayTimes()) {
            if (pt.getId() == playtimeid)
                return pt;
        }
        return null;
    }

    private boolean findPlayTimeById(PlayTime playtime) {
        List<PlayTime> playTimeList = this.listPlayTimes();
        for (PlayTime pt : playTimeList) {
            if (pt.getId() == playtime.getId())
                return true;
        }
        return false;
    }
}
