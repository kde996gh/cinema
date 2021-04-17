package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
import javafx.collections.ObservableList;

public interface PlayTimeDAO {


    ObservableList<PlayTime> listPlayTimes();

    PlayTime save(PlayTime playTime);

    void delete(PlayTime playTime);

    ObservableList<PlayTime> getMoviePlayTimes(String movieName);

    void addRoomSeats(PlayTime playTime);

    void deleteRoomSeat(PlayTime pt);

    PlayTime getPlayTimeById(int playtimeid);
}
