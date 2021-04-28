package hu.alkfejl.controller.ticket;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketAddEditController implements Initializable {

    private Ticket ticket;
    private TicketDAO ticketDAO = new TicketDAOImpl();

    @FXML
    private Spinner<Integer> ticketType;
    @FXML
    private TextField price;
    @FXML
    private TextField lowerPrice;

    public void setTicket(Ticket t) {
        this.ticket = t;

        if (ticket.priceProperty().getValue() > 0)
            price.setText(ticket.priceProperty().getValue().toString());

        if (ticket.lowerPriceProperty().getValue() > 0)
            lowerPrice.setText(ticket.lowerPriceProperty().getValue().toString());

        SpinnerValueFactory<Integer> rowValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        if (ticket.getTicketType() >= 0)
            rowValueFactory.setValue(ticket.getTicketType());
        else
            rowValueFactory.setValue(1);

        ticketType.setValueFactory(rowValueFactory);
        ticketType.valueProperty().addListener((observableValue, integer, t1) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(t1);
            ticket.ticketTypeProperty().bindBidirectional(tmp);
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        price.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.matches("[0-9]+")) {
                IntegerProperty ip = new SimpleIntegerProperty(Integer.parseInt(t1));
                price.textProperty().setValue(t1);
                ticket.priceProperty().bind(ip);
            } else {
                if (t1.length() != 0) {
                    Utils.showWarning("Az árat számokkal kell megadni");
                    price.textProperty().setValue("");
                }
            }
        });

        lowerPrice.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.matches("[0-9]+")) {
                IntegerProperty ip = new SimpleIntegerProperty(Integer.parseInt(t1));
                lowerPrice.textProperty().setValue(t1);
                ticket.lowerPriceProperty().bind(ip);
            } else {
                if (t1.length() != 0) {
                    Utils.showWarning("Az árat számokkal kell megadni!");
                    lowerPrice.textProperty().setValue("");
                }
            }
        });
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        ticket = ticketDAO.save(ticket);
        Utils.showInfo("Sikeres mentés!");
        App.loadFXML("/fxml/ticket/ticket_window.fxml");
    }


    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/ticket/ticket_window.fxml");
    }
}
