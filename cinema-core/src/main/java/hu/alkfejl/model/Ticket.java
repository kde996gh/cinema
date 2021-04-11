package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ticket {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty price = new SimpleIntegerProperty(this, "price");
    private IntegerProperty lowerPrice = new SimpleIntegerProperty(this, "lowerPrice");
    private IntegerProperty type = new SimpleIntegerProperty(this,"type");

    public int getType() {
        return type.get();
    }

    public IntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
        this.type.set(type);
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

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getLowerPrice() {
        return lowerPrice.get();
    }

    public IntegerProperty lowerPriceProperty() {
        return lowerPrice;
    }

    public void setLowerPrice(int lowerPrice) {
        this.lowerPrice.set(lowerPrice);
    }
}
