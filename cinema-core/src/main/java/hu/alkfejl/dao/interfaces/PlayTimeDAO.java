package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
import javafx.collections.ObservableList;

public interface PlayTimeDAO {


    public ObservableList<PlayTime> listPlayTimes();

    public PlayTime save(PlayTime playTime);

    public void delete(PlayTime playTime);
}
