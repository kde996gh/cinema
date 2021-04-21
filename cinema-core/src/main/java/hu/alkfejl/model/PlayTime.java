package hu.alkfejl.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class PlayTime {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty roomName = new SimpleStringProperty(this, "roomName");
    private StringProperty movieName = new SimpleStringProperty(this, "movieName");
    private IntegerProperty ticketType = new SimpleIntegerProperty(this, "ticketType");
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

    public String getRoomName() {
        return roomName.get();
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public String getMovieName() {
        return movieName.get();
    }

    public StringProperty movieNameProperty() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public int getTicketType() {
        return ticketType.get();
    }

    public IntegerProperty ticketTypeProperty() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType.set(ticketType);
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
