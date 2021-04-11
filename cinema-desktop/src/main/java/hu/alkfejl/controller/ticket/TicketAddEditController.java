package hu.alkfejl.controller.ticket;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

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
    @FXML
    private Button saveButton;

    public void setTicket(Ticket t){
        this.ticket = t;

        price.textProperty().bindBidirectional(ticket.priceProperty(), new NumberStringConverter());
        lowerPrice.textProperty().bindBidirectional(ticket.lowerPriceProperty(), new NumberStringConverter());

        IntegerProperty tType = new SimpleIntegerProperty(ticket.getTicketType());

        SpinnerValueFactory<Integer> rowValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        rowValueFactory.setValue(ticket.getTicketType());
        ticketType.setValueFactory(rowValueFactory);
        ticketType.valueProperty().addListener((observableValue, integer, t1) -> {
            IntegerProperty tmp = new SimpleIntegerProperty(t1);
            ticket.ticketTypeProperty().bindBidirectional(tmp);
        });

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void onSave(ActionEvent actionEvent) {
        ticket = ticketDAO.save(ticket);
        App.loadFXML("/fxml/ticket/ticket_window.fxml");
    }


    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/ticket/ticket_window.fxml");
    }
}
