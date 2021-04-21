package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.UserDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String ADD_USER = "INSERT INTO USER (userName, password, email) VALUES  (?,?,?)";
    private static final String USER_CHECK = "SELECT * FROM USER WHERE email = ?";
    private static final String SELECT_ALL_USER = "SELECT * FROM USER";
    private String connectionURL;

    private Connection conn;

    private static UserDAOImpl instance;

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new UserDAOImpl();
        }
        return instance;
    }


    public UserDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public ObservableList<User> listUser(){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USER);
        ) {
            ObservableList<User> users = FXCollections.observableArrayList();

            while (rs.next()) {
                User currUser = new User();

                currUser.setId(rs.getInt("id"));
                currUser.setUserName(rs.getString("userName"));
                currUser.setPassword(rs.getString("password"));
                currUser.setEmail(rs.getString("email"));
                users.add(currUser);
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void addNewUser(User user) {
        try (PreparedStatement stmt = conn.prepareStatement(ADD_USER)) {

            String encodedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(encodedPassword);

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    @Override
    public User loginCheck(String email, String password) {

        try (
                PreparedStatement pst = conn.prepareStatement(USER_CHECK)
        ) {
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                String dbPass = rs.getString("password");

                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), dbPass);
                if (result.verified) {
                    User user = new User();
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setId(rs.getInt("id"));
                    System.out.println(user);
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nem sikerült!");
        return null;
    }

    @Override
    public boolean emailExistCheck(String email){
        for(User user : this.listUser()){
            if(user.getEmail().equals(email))
                return true;
        }
        return false;
    }

}
