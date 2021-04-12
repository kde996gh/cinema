package hu.alkfejl.controller.playtime;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.PlayTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PlayTimeAddEditController implements Initializable {


    ObservableList<String> movieNames;
    ObservableList<String> roomNames;
    ObservableList<Integer> ticketTypes;

    private PlayTimeDAO playtimedao = new PlayTimeDAOImpl();

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private Slider sliderHour;

    @FXML
    private Slider sliderMinute;

    @FXML
    private ComboBox<String> titleComboBox;

    @FXML
    private ComboBox<String> roomComboBox;

    @FXML
    private ComboBox<Integer> ticketTypeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeTextField;
    private PlayTime playTime;




    public void setPlayTime(PlayTime pt) {
        this.playTime = pt;
        movieNames = MovieDAOImpl.getInstance().listByName();
        roomNames = RoomDAOImpl.getInstance().listByName();
        ticketTypes = TicketDAOImpl.getInstance().findTicketTypes();


       // StringProperty n = new SimpleStringProperty(movieName);
        //IntegerProperty in = new SimpleIntegerProperty(MovieDAOImpl.getInstance().getIdByTitle(n.getValue()));
       String movieName = MovieDAOImpl.getInstance().findMovieNameById(playTime.getMovie_id());
        titleComboBox.getSelectionModel().select(movieName);
        titleComboBox.setItems(movieNames);
        titleComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            IntegerProperty ip = new SimpleIntegerProperty(MovieDAOImpl.getInstance().getIdByTitle(newV));
            playTime.movie_idProperty().bindBidirectional(ip);
        });

        String roomName = RoomDAOImpl.getInstance().findRoomNameById(playTime.getRoom_id());
        roomComboBox.getSelectionModel().select(roomName);
        roomComboBox.setItems(roomNames);
        roomComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            IntegerProperty ip = new SimpleIntegerProperty(RoomDAOImpl.getInstance().getIdByRoomName(newV));
            playTime.room_idProperty().bindBidirectional(ip);
            System.out.println("id :  " + playTime.room_idProperty());
        });

        Integer ticketType = TicketDAOImpl.getInstance().findTicketTypeById(playTime.getTicket_id());
        ticketTypeComboBox.getSelectionModel().select(ticketType);
        ticketTypeComboBox.setItems(ticketTypes);
        ticketTypeComboBox.valueProperty().addListener((observableValue, oldV, newV) -> {
            IntegerProperty ip = new SimpleIntegerProperty(TicketDAOImpl.getInstance().getIdByTicketType(newV));
            playTime.ticket_idProperty().bindBidirectional(ip);
            System.out.println("id :  " + playTime.ticket_idProperty());
        });

        datePicker.valueProperty().bindBidirectional(playTime.playTimeDateProperty());

//////
        StringProperty minute = new SimpleStringProperty("0");
        StringProperty hour = new SimpleStringProperty("8");
        StringProperty complete = new SimpleStringProperty();

        complete.bindBidirectional(playTime.playTimeHoursProperty());

        //óra beállítása
        SpinnerValueFactory<Integer> hourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22, 8, 1);
        //hourValueFactory.setValue(8);
        hourSpinner.setValueFactory(hourValueFactory); // spinner értékek beállítása
        hourSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerHour = new SimpleStringProperty (newV.toString());
            hour.bindBidirectional(innerHour);
            complete.setValue(hour.getValue() + ":" + minute.getValue());
            System.out.println(complete.getValue());
        });

        //perc beállítása
        SpinnerValueFactory<Integer> minuteValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0, 5);
       // minuteValueFactory.setValue(0);
        minuteSpinner.setValueFactory(minuteValueFactory); // spinner értékek beállítása
        minuteSpinner.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerMin = new SimpleStringProperty (newV.toString());
            minute.bindBidirectional(innerMin);
            complete.setValue(hour.getValue() + "." + minute.getValue());
            System.out.println(complete.getValue());

        });



        //hourValueFactory.setValue(playTime.);
        //hourSpinner
      /*
        StringProperty minute = new SimpleStringProperty("");
        StringProperty hour = new SimpleStringProperty("");
        StringProperty complete = new SimpleStringProperty();
        sliderHour.valueProperty().addListener((observableValue, oldV, newV) -> {
            StringProperty innerHour = new SimpleStringProperty (new DecimalFormat("0").format(newV));
            hour.bindBidirectional(innerHour);
           complete.setValue(hour.getValue() + ":" + minute.getValue());
            System.out.println(newV.intValue() + " from hour");
        });
        sliderMinute.setBlockIncrement(10);

        sliderMinute.valueProperty().addListener((observableValue, oldV, newV) -> {
            //new DecimalFormat("0").format(newV);
            StringProperty innerMin = new SimpleStringProperty (new DecimalFormat("0").format(newV));
            minute.bindBidirectional(innerMin);
            complete.setValue(hour.getValue() + ":" + minute.getValue());
            System.out.println(complete + " from min");
        });
*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    @FXML
    public void onSave() {
        System.out.println("megnyomtak a mentest!");
        playTime = playtimedao.save(playTime);
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/playtime/playtime_window.fxml");
    }
}
