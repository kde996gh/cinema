package hu.alkfejl.controller.room;

import com.sun.javafx.binding.BidirectionalBinding;
import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomAddEditController implements Initializable {
    private Room room;
    private RoomDAO roomdao = new RoomDAOImpl();


    @FXML
    private TextField room_name;

    @FXML
    private Spinner<Integer> room_rows;
    @FXML
    private Spinner<Integer> room_cols;

    @FXML
    private Button saveButton;


//    private SpinnerValueFactory.IntegerSpinnerValueFactory room_rows;



    public void setRoom(Room r){
        this.room = r;
        List<Room> roomList = roomdao.findAll();
        room_name.textProperty().bindBidirectional(room.nameProperty());

        // sor beállítás + bind
        SpinnerValueFactory<Integer> rowValueFactory=
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

        rowValueFactory.setValue(room.getRowNumber());
        room_rows.setValueFactory(rowValueFactory);

        room_rows.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV);
            room.rowNumberProperty().bindBidirectional(tmp);
        });
        //oszlop beállítás + bind

     //   IntegerProperty colTest = new SimpleIntegerProperty();

        SpinnerValueFactory<Integer> colValueFactory=
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

        colValueFactory.setValue(room.getColNumber());
        room_cols.setValueFactory(colValueFactory);

        room_cols.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV);
          //  colTest.bind(tmp);
            room.colNumberProperty().bindBidirectional(tmp);

          //  System.out.println("coltest: " + colTest.get());

        });



    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.disableProperty().bind(room_name.textProperty().isEmpty());
    }

    @FXML
    public void onSave() {
        room = roomdao.save(room);

        App.loadFXML("/fxml/main_window.fxml");
        System.out.println("megnyomták a terem mentést!");
    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/room/room_window.fxml");

    }
}
