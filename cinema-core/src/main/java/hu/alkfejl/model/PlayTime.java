package hu.alkfejl.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class PlayTime {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty room_id = new SimpleIntegerProperty(this, "room_id");
    private IntegerProperty movie_id = new SimpleIntegerProperty(this, "movie_id");
    private IntegerProperty ticket_id = new SimpleIntegerProperty(this, "ticket_id");
    private ObjectProperty<LocalDate> playTimeDate = new SimpleObjectProperty<>(this, "playTimeDate");
    private StringProperty playTimeHours = new SimpleStringProperty(this, "playTimeHours");

    public String getPlayTimeHours() {
        return playTimeHours.get();
    }

    public StringProperty playTimeHoursProperty() {
        return playTimeHours;
    }

    public void setPlayTimeHours(String playTimeHours) {
        this.playTimeHours.set(playTimeHours);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRoom_id() {
        return room_id.get();
    }

    public IntegerProperty room_idProperty() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id.set(room_id);
    }

    public int getMovie_id() {
        return movie_id.get();
    }

    public IntegerProperty movie_idProperty() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id.set(movie_id);
    }

    public int getTicket_id() {
        return ticket_id.get();
    }

    public IntegerProperty ticket_idProperty() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id.set(ticket_id);
    }

    public LocalDate getPlayTimeDate() {
        return playTimeDate.get();
    }

    public ObjectProperty<LocalDate> playTimeDateProperty() {
        return playTimeDate;
    }

    public void setPlayTimeDate(LocalDate playTimeDate) {
        this.playTimeDate.set(playTimeDate);
    }
}
