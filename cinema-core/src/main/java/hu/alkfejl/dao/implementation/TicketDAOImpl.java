package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.Ticket;

import javax.management.relation.RelationSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    private static final String SELECT_ALL_TICKET = "SELECT * FROM TICKET";
    private static final String UPDATE_TICKET = "UPDATE TICKET SET price=?, lowerPrice=?, ticketType=? where id=?";
    private static final String INSERT_TICKET = "INSERT INTO TICKET (price, lowerPrice, ticketType) VALUES (?,?,?)";
    private static final String DELETE_TICKET = "DELETE FROM TICKET WHERE id=?";

    private String connectionURL;
    private Connection conn;
    private static TicketDAOImpl instance = new TicketDAOImpl();

    public static TicketDAOImpl getInstance() {
        return instance;
    }


    public TicketDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Ticket> findAllTicket() {

        List<Ticket> result = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
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
    public Ticket save(Ticket ticket) {
        try (//PDATE TICKET SET price=?, lowerPrice=?, ticketType=? where id=?
             PreparedStatement stmt = ticket.getId() <= 0 ? conn.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_TICKET)
        ) {
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
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_TICKET)) {
            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
