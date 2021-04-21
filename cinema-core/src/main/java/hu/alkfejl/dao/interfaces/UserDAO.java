package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.User;

public interface UserDAO {

    void addNewUser(User user);

    User loginCheck(String username, String password);


}
