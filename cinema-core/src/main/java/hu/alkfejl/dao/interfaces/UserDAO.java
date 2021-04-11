package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.User;

import java.util.List;

public interface UserDAO {

    public List<User> listAllUser();
    public User getUserById(int id);
    public void addNewUser(User user);
    public void deleteUser(User user);


}
