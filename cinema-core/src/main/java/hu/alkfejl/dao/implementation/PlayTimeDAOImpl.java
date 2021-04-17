package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class PlayTimeDAOImpl implements PlayTimeDAO {

    private static final String SELECT_ALL_PLAYTIME = "SELECT * FROM PLAYTIME";
    private static final String INSERT_PLAYTIME = "INSERT INTO PLAYTIME (room_name, movie_name, ticket_type, playTimeDate, playTimeHours) values (?,?,?,?,?)";
    private static final String UPDATE_PLAYTIME = "UPDATE PLAYTIME SET room_name=?, movie_name=?, ticket_type=?, playTimeDate=?, playTimeHours=? WHERE id=?";
    private static final String DELETE_PLAYTIME = "DELETE FROM PLAYTIME WHERE id=?";

    private static final String DELETE_SEAT_WITH_ID = "DELETE FROM SEAT WHERE playtime_id=?";
    private static final String INSERT_SEATS = "INSERT INTO SEAT (playtime_id, seat_id, taken) VALUES (?,?,?)";
    private static final String SELECT_SPEC_MOVIES = "SELECT * FROM PLAYTIME WHERE movie_name=?";
    private String connectionURL;
    private Connection conn;

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
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ObservableList<PlayTime> listPlayTimes() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PLAYTIME);
        ) {
            ObservableList<PlayTime> playtimes = FXCollections.observableArrayList();

            while (rs.next()) {
                PlayTime pt = new PlayTime();

                pt.setId(rs.getInt("id"));
                pt.setRoom_name(rs.getString("room_name"));
                pt.setMovie_name(rs.getString("movie_name"));
                pt.setTicket_type(rs.getInt("ticket_type"));

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
        try (
                //    private static final String UPDATE_PLAYTIME = "UPDATE PLAYTIME SET room_id=?,
                //    movie_id=?, ticket_id=?, playTimeDate=?, playTimeHours=? WHERE id=?";
                PreparedStatement stmt = playTime.getId() <= 0 ? conn.prepareStatement(INSERT_PLAYTIME, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_PLAYTIME)
        ) {
            //INSERT INTO PLAYTIME (room_name, movie_name, ticket_type, playTimeDate, playTimeHours) values (?,?,?,?,?)
            if (playTime.getId() > 0) {
                stmt.setInt(6, playTime.getId());
            }
            stmt.setString(1, playTime.getRoom_name());
            stmt.setString(2, playTime.getMovie_name());
            stmt.setInt(3, playTime.getTicket_type());
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
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_PLAYTIME)) {
            stmt.setInt(1, playTime.getId());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public ObservableList<PlayTime> getMoviePlayTimes(String movieName) {
        ObservableList<PlayTime> result = FXCollections.observableArrayList();
        ObservableList<PlayTime> pts = this.listPlayTimes();
        for(PlayTime ptit : pts){
            if(ptit.getMovie_name().equals(movieName)){
                result.add(ptit);
            }
        }
        return result;
    }

    @Override
    public void addRoomSeats(PlayTime playTime) {
        try {
            PreparedStatement stmt;
            Room r = RoomDAOImpl.getInstance().getRoomByName(playTime.getRoom_name());

            if (findPlayTimeById(playTime)) {
                stmt = conn.prepareStatement(DELETE_SEAT_WITH_ID);
                stmt.setInt(1, playTime.getId());
                stmt.executeUpdate();
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < r.getSeatNumber() + 1; i++) {
                    //"INSERT INTO SEAT (room_id, seat_id, taken) VALUES (?,?,?)";

                    stmt.setInt(1, playTime.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();
                }
                //System.out.println("Megtaláltam!");
            } else {
                stmt = conn.prepareStatement(INSERT_SEATS);
                for (int i = 1; i < r.getSeatNumber() + 1; i++) {
                    stmt.setInt(1, playTime.getId());
                    stmt.setInt(2, i);
                    stmt.setInt(3, 0);
                    stmt.executeUpdate();

                }
                //  System.out.println("Nem találtam meg a szobát!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // return room;
    }

    public void deleteRoomSeat(PlayTime pt) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SEAT_WITH_ID)) {
            stmt.setInt(1, pt.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a szék törlésekor!");
        }
    }

    @Override
    public PlayTime getPlayTimeById(int playtimeid) {
        for(PlayTime pt : this.listPlayTimes()){
            if(pt.getId() == playtimeid){
                return pt;
            }
        }
        return null;
    }

    private boolean findPlayTimeById(PlayTime playtime) {
        List<PlayTime> playTimeList = this.listPlayTimes();
        for (PlayTime pt : playTimeList) {
            if (pt.getId() == playtime.getId()) {
                return true;
            }
        }
        return false;
    }
}
