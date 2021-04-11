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
    private Connection conn;
    private String connectionURL;


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
                current.setType(rs.getInt("type"));
                result.add(current);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return result;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }

    @Override
    public void delete(Ticket ticket) {

    }
}
