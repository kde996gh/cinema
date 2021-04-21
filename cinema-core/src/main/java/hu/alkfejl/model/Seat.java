package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Seat {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtimeId = new SimpleIntegerProperty(this, "playtimeId");
    private IntegerProperty seatId = new SimpleIntegerProperty(this, "seatId");
    private IntegerProperty taken = new SimpleIntegerProperty(this, "taken");

    public int getTaken() {
        return taken.get();
    }

    public IntegerProperty takenProperty() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken.set(taken);
    }

    public int getSeatId() {
        return seatId.get();
    }

    public IntegerProperty seatIdProperty() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId.set(seatId);
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

    public int getPlaytimeId() {
        return playtimeId.get();
    }

    public IntegerProperty playtimeIdProperty() {
        return playtimeId;
    }

    public void setPlaytimeId(int playtimeId) {
        this.playtimeId.set(playtimeId);
    }
}
