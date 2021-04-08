package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty title = new SimpleStringProperty(this, "title");
    private IntegerProperty length = new SimpleIntegerProperty(this, "length");
    private IntegerProperty ageLimit = new SimpleIntegerProperty(this, "ageLimit");
    private StringProperty director = new SimpleStringProperty(this, "director");
    private StringProperty actors = new SimpleStringProperty(this, "actors");

    public String getActors() {
        return actors.get();
    }

    public StringProperty actorsProperty() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors.set(actors);
    }

    private StringProperty description = new SimpleStringProperty(this, "description");
    private StringProperty coverImage = new SimpleStringProperty(this, "coverImage");
    private IntegerProperty movieType = new SimpleIntegerProperty(this, "movieType");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getLength() {
        return length.get();
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public int getAgeLimit() {
        return ageLimit.get();
    }

    public IntegerProperty ageLimitProperty() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit.set(ageLimit);
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCoverImage() {
        return coverImage.get();
    }

    public StringProperty coverImageProperty() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage.set(coverImage);
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
}
