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

        //System.out.println(pt.getMovie_name());
        //System.out.println(pt.getRoom_name());
      //  System.out.println(pt.getTicket_type());


        titleComboBox.getSelectionModel().select(playTime.getMovie_name());
        titleComboBox.setItems(movieNames);
        titleComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty sp = new SimpleStringProperty(newV);
            playTime.movie_nameProperty().bindBidirectional(sp);
        });

        roomComboBox.getSelectionModel().select(playTime.getRoom_name());
        roomComboBox.setItems(roomNames);
        roomComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty sp = new SimpleStringProperty(newV);
            playTime.room_nameProperty().bindBidirectional(sp);
        });

        //System.out.println(playTime.getTicket_type() + " jegy tipusa!");
        ticketTypeComboBox.setItems(ticketTypes);
        if (playTime.getTicket_type() != 0) {

           // ticketTypeComboBox.getSelectionModel().select(playTime.getTicket_type());
            ticketTypeComboBox.getSelectionModel().selectNext();
        }

        ticketTypeComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            IntegerProperty ip = new SimpleIntegerProperty(newV);
            playTime.ticket_typeProperty().bindBidirectional(ip);
        });

        datePicker.valueProperty().bindBidirectional(playTime.playTimeDateProperty());

//////
        StringProperty minute = new SimpleStringProperty("0");
        StringProperty hour = new SimpleStringProperty("8");
        StringProperty complete = new SimpleStringProperty();

        complete.bindBidirectional(playTime.playTimeHoursProperty());
        timeLabel.textProperty().bindBidirectional(playTime.playTimeHoursProperty());
        //óra beállítása
        SpinnerValueFactory<Integer> hourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22, 8, 1);
        //hourValueFactory.setValue(8);
        hourSpinner.setValueFactory(hourValueFactory); // spinner értékek beállítása
        hourSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerHour = new SimpleStringProperty(newV.toString());
            hour.bindBidirectional(innerHour);
            // int a = hour.getValue();
            complete.setValue(((Integer.parseInt(hour.getValue()) > 9) ? hour.getValue() : "0" + hour.getValue()) + ":" + ((Integer.parseInt(minute.getValue()) > 9) ? minute.getValue() : "0" + minute.getValue()));
            //System.out.println(complete.getValue());
        });

        //perc beállítása
        SpinnerValueFactory<Integer> minuteValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        // minuteValueFactory.setValue(0);
        minuteSpinner.setValueFactory(minuteValueFactory); // spinner értékek beállítása
        minuteSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerMin = new SimpleStringProperty(newV.toString());
            minute.bindBidirectional(innerMin);
            complete.setValue(((Integer.parseInt(hour.getValue()) > 9) ? hour.getValue() : "0" + hour.getValue()) + ":" + ((Integer.parseInt(minute.getValue()) > 9) ? minute.getValue() : "0" + minute.getValue()));
         //   System.out.println(complete.getValue());

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
        Utils.showInfo("Sikeres mentés!");
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }
}
