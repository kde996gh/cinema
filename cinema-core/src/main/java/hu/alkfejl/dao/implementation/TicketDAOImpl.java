package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    private static final String SELECT_ALL_TICKET = "SELECT * FROM TICKET";
    private static final String UPDATE_TICKET = "UPDATE TICKET SET price=?, lowerPrice=?, ticketType=? where id=?";
    private static final String INSERT_TICKET = "INSERT INTO TICKET (price, lowerPrice, ticketType) VALUES (?,?,?)";
    private static final String DELETE_TICKET = "DELETE FROM TICKET WHERE id=?";
    private static final String SELECT_ONLY_NAMES = "SELECT ticketType FROM TICKET";

    private final String connectionURL = CinemaConfiguration.getValue("db.url");

    private static TicketDAOImpl instance;

    public static TicketDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new TicketDAOImpl();
        }
        return instance;
    }


    public TicketDAOImpl() {
    }

    @Override
    public List<Ticket> findAllTicket() {
        List<Ticket> result = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_TICKET);) {
            while (rs.next()) {
                Ticket current = new Ticket();
                current.setId(rs.getInt("id"));
                current.setPrice(rs.getInt("price"));
                current.setLowerPrice(rs.getInt("lowerPrice"));
                current.setTicketType(rs.getInt("ticketType"));
                result.add(current);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    @Override
    public ObservableList<Integer> findTicketTypes() {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ONLY_NAMES)
        ) {
            while (rs.next()) {
                Integer a = rs.getInt("ticketType");
                result.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Ticket save(Ticket ticket) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = ticket.getId() <= 0 ? conn.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_TICKET)) {
            if (ticket.getId() > 0) {
                stmt.setInt(4, ticket.getId());
            }
            stmt.setInt(1, ticket.getPrice());
            stmt.setInt(2, ticket.getLowerPrice());
            stmt.setInt(3, ticket.getTicketType());
            stmt.executeUpdate();

            if (ticket.getId() <= 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        try (
                Connection conn = DriverManager.getConnection(connectionURL);
                PreparedStatement stmt = conn.prepareStatement(DELETE_TICKET)) {
            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getTicketByType(int ticket_type) {
        for (Ticket ticket : this.findAllTicket()) {
            if (ticket.getTicketType() == ticket_type)
                return ticket;
        }
        return null;
    }
}
