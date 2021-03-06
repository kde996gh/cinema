package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;


public class MovieEditController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField length;
    @FXML
    private TextField ageLimit;
    @FXML
    private TextField director;
    @FXML
    private TextArea actors;
    @FXML
    private TextArea description;
    @FXML
    private ImageView coverImage;
    @FXML
    private Button newCoverImage;

    @FXML
    private Button saveButton;

    private Movie movie;
    private MovieDAO moviedao = new MovieDAOImpl();
    private StringProperty newImage = new SimpleStringProperty();


    public void setMovie(Movie m) {
        this.movie = m;

        title.textProperty().bindBidirectional(movie.titleProperty());

        if (movie.lengthMinProperty().getValue() > 0)
            length.setText(movie.lengthMinProperty().getValue().toString());
        else
            length.setText("");


        if (movie.ageLimitProperty().getValue() > 0)
            ageLimit.setText(movie.ageLimitProperty().getValue().toString());
        else
            ageLimit.setText("");
        director.textProperty().bindBidirectional(movie.directorProperty());
        actors.textProperty().bindBidirectional(movie.actorsProperty());
        description.textProperty().bindBidirectional(movie.descriptionProperty());

        newImage.bindBidirectional(movie.coverImageProperty());
        if (newImage.getValue() != null) {
            try {
                coverImage.setImage(getImageFromBase64String(newImage.getValue()));
            } catch (IOException e) {
                System.err.println("Hiba a k??p bet??lt??sekor!");
                Utils.showWarning("Hiba a k??p bet??lt??sekor!");
            }
        }
        newImage.addListener((observable, oldV, newV) -> {
            try {
                coverImage.setImage(getImageFromBase64String(newV));
            } catch (IOException e) {
                System.err.println("Hiba az ??j k??p bet??lt??sekor!!");
                Utils.showWarning("Hiba az ??j k??p bet??lt??sekor!");
            }
        });
    }

    public void backToMovies() {
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newCoverImage.setOnAction(actionEvent -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png, *.jpg", "*.png", "*.jpg"));
                File file = fileChooser.showOpenDialog(null);
                newImage.set(encodeFileToBase64Binary(file));
            } catch (Exception e) {
                Utils.showWarning("Hiba t??rt??nt a k??p beolvas??sakor!");
                System.err.println("Hiba t??rt??nt a k??p beolvas??sakor!");
            }
        });

        length.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.matches("[0-9]+")) {
                IntegerProperty ip = new SimpleIntegerProperty(Integer.parseInt(t1));
                length.textProperty().setValue(t1);
                movie.lengthMinProperty().bind(ip);
            } else {
                if (t1.length() != 0) {
                    Utils.showWarning("A hosszt percben kell megadni!");
                    length.textProperty().setValue("");

                }
            }
        });

        ageLimit.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.matches("[0-9]+")) {
                IntegerProperty ip = new SimpleIntegerProperty(Integer.parseInt(t1));
                ageLimit.textProperty().setValue(t1);
                movie.ageLimitProperty().bind(ip);
            } else {
                if (t1.length() != 0) {
                    Utils.showWarning("A korhat??r csak sz??mot tartalmazhat!!");
                    ageLimit.textProperty().setValue("");
                }
            }
        });

        saveButton.disableProperty().bind(title.textProperty().isEmpty()
                .or(length.textProperty().isEmpty())
                .or(ageLimit.textProperty().isEmpty())
                .or(director.textProperty().isEmpty())
                .or(actors.textProperty().isEmpty())
                .or(description.textProperty().isEmpty())
                .or(coverImage.imageProperty().isNull())
        );
    }

    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        } catch (IOException e) {
            System.err.println("Hiba a k??p base64-gy?? alak??t??sakor!");
        }

        return encodedfile;
    }

    private Image getImageFromBase64String(String newValue) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(newValue));
        return new Image(inputStream);
    }

    @FXML
    public void saveMovie() {
        movie = moviedao.save(movie);
        Utils.showInfo("Sikeres ment??s!");
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }

    public void deleteMovie(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan t??rl??d a filmet", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                moviedao.delete(movie);
            }
        });
        Utils.showInfo("Sikeres t??rl??s!");
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }
}
