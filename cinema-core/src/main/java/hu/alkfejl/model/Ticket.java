package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ticket {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private IntegerProperty movieType = new SimpleIntegerProperty(this, "movieType");
    private IntegerProperty price = new SimpleIntegerProperty(this, "price");
    private IntegerProperty lowerPrice = new SimpleIntegerProperty(this, "lowerPrice");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getMovieType() {
        return movieType.get();
    }

    public IntegerProperty movieTypeProperty() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType.set(movieType);
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
