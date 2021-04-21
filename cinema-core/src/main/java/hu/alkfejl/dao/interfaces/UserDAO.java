package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.User;
import javafx.collections.ObservableList;

public interface UserDAO {

    ObservableList<User> listUser();

    void addNewUser(User user);

    User loginCheck(String username, String password);


    boolean emailExistCheck(String email);
}
