package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtimeId = new SimpleIntegerProperty(this, "playtimeId");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private StringProperty reservedSeat = new SimpleStringProperty(this, "reservedSeat");
    private IntegerProperty priceSum = new SimpleIntegerProperty(this, "priceSum");
    private StringProperty movieName = new SimpleStringProperty(this, "movieName");
    private StringProperty playtimeDate = new SimpleStringProperty(this, "playtimeDate");


    public String getMovieName() {
        return movieName.get();
    }

    public StringProperty movieNameProperty() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public String getPlaytimeDate() {
        return playtimeDate.get();
    }

    public StringProperty playtimeDateProperty() {
        return playtimeDate;
    }

    public void setPlaytimeDate(String playtimeDate) {
        this.playtimeDate.set(playtimeDate);
    }

    public int getPriceSum() {
        return priceSum.get();
    }

    public IntegerProperty priceSumProperty() {
        return priceSum;
    }

    public void setPriceSum(int priceSum) {
        this.priceSum.set(priceSum);
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


    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getReservedSeat() {
        return reservedSeat.get();
    }

    public StringProperty reservedSeatProperty() {
        return reservedSeat;
    }

    public void setReservedSeat(String reservedSeat) {
        this.reservedSeat.set(reservedSeat);
    }
}

