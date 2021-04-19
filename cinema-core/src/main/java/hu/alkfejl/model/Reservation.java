package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtime_id = new SimpleIntegerProperty(this, "playtime_id");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private StringProperty reserved_seat = new SimpleStringProperty(this, "reserved_seat");
    private IntegerProperty price_sum = new SimpleIntegerProperty(this, "price_sum");

    public int getPrice_sum() {
        return price_sum.get();
    }

    public IntegerProperty price_sumProperty() {
        return price_sum;
    }

    public void setPrice_sum(int price_sum) {
        this.price_sum.set(price_sum);
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


    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getReserved_seat() {
        return reserved_seat.get();
    }

    public StringProperty reserved_seatProperty() {
        return reserved_seat;
    }

    public void setReserved_seat(String reserved_seat) {
        this.reserved_seat.set(reserved_seat);
    }
}

