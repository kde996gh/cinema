package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtime_id = new SimpleIntegerProperty(this, "playtime_id");
    private IntegerProperty price = new SimpleIntegerProperty(this, "price");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private IntegerProperty reserved_seat = new SimpleIntegerProperty(this, "reserved_seat");

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

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getReserved_seat() {
        return reserved_seat.get();
    }

    public IntegerProperty reserved_seatProperty() {
        return reserved_seat;
    }

    public void setReserved_seat(int reserved_seat) {
        this.reserved_seat.set(reserved_seat);
    }
}

