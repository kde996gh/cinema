package hu.alkfejl.controller.room;

import com.sun.javafx.binding.BidirectionalBinding;
import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.beans.property.*;
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

    @FXML
    private TextField room_seats;


//    private SpinnerValueFactory.IntegerSpinnerValueFactory room_rows;



    public void setRoom(Room r){
        this.room = r;
        List<Room> roomList = roomdao.findAll();
        room_name.textProperty().bindBidirectional(room.nameProperty());
        IntegerProperty asda = new SimpleIntegerProperty(room.getSeatNumber());
        room_seats.setText(asda.getValue().toString());
        IntegerProperty colTest = new SimpleIntegerProperty(room.getColNumber());

        // sor beállítás + bind
        IntegerProperty rowTest = new SimpleIntegerProperty(room.getRowNumber());

        SpinnerValueFactory<Integer> rowValueFactory=
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

        rowValueFactory.setValue(room.getRowNumber());
        room_rows.setValueFactory(rowValueFactory);

        room_rows.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV);
            rowTest.bind(tmp);
            asda.setValue(colTest.getValue() * rowTest.getValue());
            room_seats.setText(asda.getValue().toString());

            room.rowNumberProperty().bindBidirectional(tmp);
        });
        //oszlop beállítás + bind



        SpinnerValueFactory<Integer> colValueFactory=
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

        colValueFactory.setValue(room.getColNumber());
        room_cols.setValueFactory(colValueFactory);

        room_cols.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV);
            colTest.bind(tmp);
            room.colNumberProperty().bindBidirectional(tmp);
            asda.setValue(colTest.getValue() * rowTest.getValue());
            room_seats.setText(asda.getValue().toString());
            System.out.println(asda.getValue());
        });

        System.out.println(colTest.getValue() * rowTest.getValue());
      //  IntegerProperty asda = new SimpleIntegerProperty(colTest.getValue() * rowTest.getValue());

        room.seatNumberProperty().bindBidirectional(asda);








          //  System.out.println("coltest: " + colTest.get());
           // colTest.multiply(rowTest);



        /*
             IntegerProperty yzt = new SimpleIntegerProperty(Integer.parseInt(room_seats.getText()));
            IntegerProperty yzt2 = new SimpleIntegerProperty(colTest.multiply(rowTest));
            room.seatNumberProperty().bindBidirectional(yzt);
        room_seats.textProperty().bind((colTest.multiply(rowTest)).asString());
        room_seats.textProperty().bind((colTest.multiply(rowTest)).asString());

        IntegerProperty xd = new SimpleIntegerProperty(Integer.parseInt(room_seats.getText()));

        room.seatNumberProperty().bindBidirectional(Integer.parseInt(room_seats.getText()));

        System.out.println("fikás csöcs" + Integer.parseInt(room_seats.getText()));*/

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
