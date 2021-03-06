package hu.alkfejl.controller.playtime;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.PlayTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayTimeAddEditController implements Initializable {


    ObservableList<String> movieNames;
    ObservableList<String> roomNames;
    ObservableList<Integer> ticketTypes;

    private PlayTimeDAO playtimedao = new PlayTimeDAOImpl();

    @FXML
    private Button saveButton;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private ComboBox<String> titleComboBox;

    @FXML
    private ComboBox<String> roomComboBox;

    @FXML
    private ComboBox<Integer> ticketTypeComboBox;

    @FXML
    private DatePicker datePicker;


    @FXML
    private Label timeLabel;

    @FXML
    private Label alertLabel;


    private PlayTime playTime;


    public void setPlayTime(PlayTime pt) {
        this.playTime = pt;
        movieNames = MovieDAOImpl.getInstance().listByName();
        roomNames = RoomDAOImpl.getInstance().listByName();
        ticketTypes = TicketDAOImpl.getInstance().findTicketTypes();

        titleComboBox.getSelectionModel().select(playTime.getMovieName());
        titleComboBox.setItems(movieNames);
        titleComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty sp = new SimpleStringProperty(newV);
            playTime.movieNameProperty().bindBidirectional(sp);
        });

        roomComboBox.getSelectionModel().select(playTime.getRoomName());
        roomComboBox.setItems(roomNames);
        roomComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty sp = new SimpleStringProperty(newV);
            playTime.roomNameProperty().bindBidirectional(sp);
        });

        ticketTypeComboBox.setItems(ticketTypes);
        if (playTime.getTicketType() != 0) {
            ticketTypeComboBox.getSelectionModel().selectNext();
        }

        ticketTypeComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            IntegerProperty ip = new SimpleIntegerProperty(newV);
            playTime.ticketTypeProperty().bindBidirectional(ip);
        });

        datePicker.valueProperty().bindBidirectional(playTime.playTimeDateProperty());

        StringProperty minute = new SimpleStringProperty("0");
        StringProperty hour = new SimpleStringProperty("8");
        StringProperty complete = new SimpleStringProperty();

        complete.bindBidirectional(playTime.playTimeHoursProperty());
        timeLabel.textProperty().bindBidirectional(playTime.playTimeHoursProperty());
        //??ra be??ll??t??sa
        SpinnerValueFactory<Integer> hourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22, 8, 1);
        hourSpinner.setValueFactory(hourValueFactory); // spinner ??rt??kek be??ll??t??sa
        hourSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerHour = new SimpleStringProperty(newV.toString());
            hour.bindBidirectional(innerHour);
            complete.setValue(((Integer.parseInt(hour.getValue()) > 9) ? hour.getValue() : "0" + hour.getValue()) + ":" + ((Integer.parseInt(minute.getValue()) > 9) ? minute.getValue() : "0" + minute.getValue()));
        });

        //perc be??ll??t??sa
        SpinnerValueFactory<Integer> minuteValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        minuteSpinner.setValueFactory(minuteValueFactory); // spinner ??rt??kek be??ll??t??sa
        minuteSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerMin = new SimpleStringProperty(newV.toString());
            minute.bindBidirectional(innerMin);
            complete.setValue(((Integer.parseInt(hour.getValue()) > 9) ? hour.getValue() : "0" + hour.getValue()) + ":" + ((Integer.parseInt(minute.getValue()) > 9) ? minute.getValue() : "0" + minute.getValue()));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveButton.disableProperty().bind(titleComboBox.valueProperty().isNull()
                .or(roomComboBox.valueProperty().isNull())
                .or(ticketTypeComboBox.valueProperty().isNull()
                        .or(timeLabel.textProperty().isEmpty())
                        .or(datePicker.valueProperty().isNull()))

        );
        alertLabel.disableProperty().bind(titleComboBox.valueProperty().isNotNull()
                .and(roomComboBox.valueProperty().isNotNull())
                .and(ticketTypeComboBox.valueProperty().isNotNull()
                        .and(timeLabel.textProperty().isNotEmpty())
                        .and(datePicker.valueProperty().isNotNull()))

        );

    }

    @FXML
    public void onSave() {
        playTime = playtimedao.save(playTime);
        playtimedao.addRoomSeats(playTime);
        Utils.showInfo("Sikeres ment??s!");
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }
}
