package hu.alkfejl.controller.room;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
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
    private Label room_seats;

    public void setRoom(Room r) {
        this.room = r;

        room_name.textProperty().bindBidirectional(room.nameProperty());//szoba nevének betölése 2way bindinggel
        IntegerProperty seatProp = new SimpleIntegerProperty(room.getSeatNumber());// uj prop létrehozása a szoba méretével

        if (room.getSeatNumber() > 0)
            room_seats.setText(seatProp.getValue().toString()); // az érték beállytása hogy az fxmlen is megjelenjen
        else
            room_seats.setText("1");

        IntegerProperty colTest = new SimpleIntegerProperty(room.getColNumber()); //az oszlop szám lekérése
        IntegerProperty rowTest = new SimpleIntegerProperty(room.getRowNumber()); // a sor számok lekérése

        // sor beállítás + bind
        //sor spinner méret beállítása
        SpinnerValueFactory<Integer> rowValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        if (room.getRowNumber() > 0)
            rowValueFactory.setValue(room.getRowNumber()); // a meglévő alapérték beállítása
        else
            rowValueFactory.setValue(1);
        room_rows.setValueFactory(rowValueFactory); // spinner értékek beállítása
        //spinner érték változásának lekövetése
        room_rows.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV); // ideiglenes property az aktuális érték eltárolására
            rowTest.bind(tmp);                                     // az oszlopérték az új értéktől függ
            seatProp.setValue(colTest.getValue() * rowTest.getValue()); // az aktuális sor mérettől függő székszám beállítása
            room_seats.setText(seatProp.getValue().toString());  // a field ami kiirja a székek számát realtime frissuljön ezért ujra van álltiva itt
            room.rowNumberProperty().bindBidirectional(tmp); // a sorok számát kötöm az aktuálishoz
        });
        //oszlop beállítás + bind
        //oszlop spinner méret beállítása

        SpinnerValueFactory<Integer> colValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);

        if (room.getRowNumber() > 0)
            colValueFactory.setValue(room.getColNumber()); // a meglévő érték beállítása kezdőértéknek
        else
            colValueFactory.setValue(1); // a meglévő érték beállítása kezdőértéknek

        room_cols.setValueFactory(colValueFactory);    // a spinner értékek beállítása
        //az oszlop spinner érték változásának lekövetése
        room_cols.valueProperty().addListener((observable, oldV, newV) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(newV);// ideiglenes property az aktuális érték eltárolására
            colTest.bind(tmp);                                    //az uj oszlop érték kötése, a későbbi szék szám szorzás végett
            room.colNumberProperty().bindBidirectional(tmp);      // az oszlopok számát kötöm az aktuális objekthez
            seatProp.setValue(colTest.getValue() * rowTest.getValue()); // az aktuális oszlop mérettől függő székszám beállítása
            room_seats.setText(seatProp.getValue().toString()); //a field ami kiirja a székek számát realtime frissüljön oszlop méret állításkor is
        });

        room.seatNumberProperty().bindBidirectional(seatProp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.disableProperty().bind(room_name.textProperty().isEmpty());
    }

    @FXML
    public void onSave() {
        if(room.getRowNumber() > room.getColNumber()){
            Utils.showWarning("Téglalap alakú elrendezést kell követni, változtass a méreten!");
        }else {
            room = roomdao.save(room);
            Utils.showInfo("Sikeres mentés!");
            App.loadFXML("/fxml/room/room_window.fxml");
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/room/room_window.fxml");

    }
}
