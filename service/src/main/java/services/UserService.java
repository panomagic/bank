package services;

import beans.User;
import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUserByID(Integer id);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    List<User> getAllUsers();
}