package hu.alkfejl.controller.ticket;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TicketWindowController implements Initializable {

    TicketDAO ticketDAO = new TicketDAOImpl();
    private List<Ticket> tickets;

    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketTypeColumn;
    @FXML
    private TableColumn<Ticket, Integer> priceColumn;
    @FXML
    private TableColumn<Ticket, Integer> lowerPriceColumn;
    @FXML
    private TableColumn<Ticket, Void> actionsColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();

        ticketTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        lowerPriceColumn.setCellValueFactory(new PropertyValueFactory<>("lowerPrice"));

        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button editButton = new Button("Módosítás");

            {
                deleteButton.setOnAction(event -> {
                    Ticket ticket = getTableRow().getItem();
                    deleteTicket(ticket);// törlés
                    refreshTable(); // táblafrissites
                });

                editButton.setOnAction(event -> {
                    Ticket ticket = getTableRow().getItem();
                    editRoom(ticket);

                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editButton, deleteButton);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }

        });
    }

    private void editRoom(Ticket ticket) {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/ticket/ticket_add_edit.fxml"));
        TicketAddEditController controller = fxmlLoader.getController();
        controller.setTicket(ticket);
    }

    public void newTicket() {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/ticket/ticket_add_edit.fxml"));
        TicketAddEditController controller = fxmlLoader.getController();
        controller.setTicket(new Ticket());
    }

    private void deleteTicket(Ticket ticket) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törlöd a jegy típust?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(buttonType.YES)) {
                ticketDAO.delete(ticket);
            }
        });
        Utils.showInfo("Sikeres törlés");
    }

    private void refreshTable() {
        tickets = ticketDAO.findAllTicket();
        ticketTable.getItems().setAll(tickets);
    }

    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");

    }
}
