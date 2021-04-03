package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty playtime_id = new SimpleIntegerProperty(this, "playtime_id");
    private StringProperty name = new SimpleStringProperty(this, "name");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private IntegerProperty age = new SimpleIntegerProperty(this, "age");
    private IntegerProperty reservedTickets = new SimpleIntegerProperty(this, "reservedTickets");

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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public int getReservedTickets() {
        return reservedTickets.get();
    }

    public IntegerProperty reservedTicketsProperty() {
        return reservedTickets;
    }

    public void setReservedTickets(int reservedTickets) {
        this.reservedTickets.set(reservedTickets);
    }
}
