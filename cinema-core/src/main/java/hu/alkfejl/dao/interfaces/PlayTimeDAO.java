package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.PlayTime;
import javafx.collections.ObservableList;

import java.util.List;

public interface PlayTimeDAO {


    List<PlayTime> listPlayTimes();

    PlayTime save(PlayTime playTime);

    void delete(PlayTime playTime);

    List<PlayTime> getMoviePlayTimes(String movieName);

    void addRoomSeats(PlayTime playTime);

    void deleteRoomSeat(PlayTime pt);

    PlayTime getPlayTimeById(int playtimeid);
}
