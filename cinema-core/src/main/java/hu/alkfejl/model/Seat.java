package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Seat {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtime_id = new SimpleIntegerProperty(this, "playtime_id");
    private IntegerProperty seat_id = new SimpleIntegerProperty(this, "seat_id");
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

    public int getSeat_id() {
        return seat_id.get();
    }

    public IntegerProperty seat_idProperty() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id.set(seat_id);
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

    public int getPlaytime_id() {
        return playtime_id.get();
    }

    public IntegerProperty playtime_idProperty() {
        return playtime_id;
    }

    public void setPlaytime_id(int playtime_id) {
        this.playtime_id.set(playtime_id);
    }
}
