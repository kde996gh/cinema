package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.User;
import javafx.collections.ObservableList;

import java.util.List;

public interface UserDAO {

    List<User> listUser();

    void addNewUser(User user);

    User loginCheck(String username, String password);


    boolean emailExistCheck(String email);
}
