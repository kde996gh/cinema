package hu.alkfejl.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class PlayTime {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty room_name = new SimpleStringProperty(this, "room_name");
    private StringProperty movie_name = new SimpleStringProperty(this, "movie_name");
    private IntegerProperty ticket_type = new SimpleIntegerProperty(this, "ticket_type");
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

    public String getRoom_name() {
        return room_name.get();
    }

    public StringProperty room_nameProperty() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name.set(room_name);
    }

    public String getMovie_name() {
        return movie_name.get();
    }

    public StringProperty movie_nameProperty() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name.set(movie_name);
    }

    public int getTicket_type() {
        return ticket_type.get();
    }

    public IntegerProperty ticket_typeProperty() {
        return ticket_type;
    }

    public void setTicket_type(int ticket_type) {
        this.ticket_type.set(ticket_type);
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
