package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty rowNumber = new SimpleIntegerProperty(this, "rowNumber");
    private IntegerProperty colNumber = new SimpleIntegerProperty(this, "colNumber");
    private StringProperty name = new SimpleStringProperty(this, "name");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRowNumber() {
        return rowNumber.get();
    }

    public IntegerProperty rowNumberProperty() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber.set(rowNumber);
    }

    public int getColNumber() {
        return colNumber.get();
    }

    public IntegerProperty colNumberProperty() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber.set(colNumber);
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

    @Override
    public String toString() {
        return "Room: " + name.getValue() + ", ferohely: " + (this.rowNumber.getValue() * this.colNumber.getValue());
    }
}
