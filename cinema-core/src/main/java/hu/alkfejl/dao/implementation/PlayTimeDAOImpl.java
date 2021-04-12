package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PlayTimeDAOImpl implements PlayTimeDAO {

    private static final String SELECT_ALL_PLAYTIME = "SELECT * FROM PLAYTIME";
    private static final String INSERT_PLAYTIME = "INSERT INTO PLAYTIME (room_name, movie_name, ticket_type, playTimeDate, playTimeHours) values (?,?,?,?,?)";
    private static final String UPDATE_PLAYTIME = "UPDATE PLAYTIME SET room_name=?, movie_name=?, ticket_type=?, playTimeDate=?, playTimeHours=? WHERE id=?";
    private static final String DELETE_PLAYTIME = "DELETE FROM PLAYTIME WHERE id=?";
    private String connectionURL;
    private Connection conn;

    private static PlayTimeDAOImpl instance = new PlayTimeDAOImpl();
    public static PlayTimeDAOImpl getInstance() {
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
        try(
                //    private static final String UPDATE_PLAYTIME = "UPDATE PLAYTIME SET room_id=?,
                //    movie_id=?, ticket_id=?, playTimeDate=?, playTimeHours=? WHERE id=?";
                PreparedStatement stmt = playTime.getId() <= 0 ? conn.prepareStatement(INSERT_PLAYTIME, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_PLAYTIME)
        ){
            //INSERT INTO PLAYTIME (room_name, movie_name, ticket_type, playTimeDate, playTimeHours) values (?,?,?,?,?)
            if(playTime.getId() > 0){
                stmt.setInt(6 ,playTime.getId());
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


        }catch (SQLException s){
            s.printStackTrace();
            return null;

        }
        return playTime;    }

    @Override
    public void delete(PlayTime playTime) {
        try(PreparedStatement stmt = conn.prepareStatement(DELETE_PLAYTIME)){
            stmt.setInt(1,playTime.getId());
            stmt.executeUpdate();
        }catch(SQLException exception){
            exception.printStackTrace();
        }

    }
}
