package services;

import beans.User;
import java.io.File;
import java.sql.Blob;
import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUserByID(Integer id);
    User getUserByName(String name);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    List<User> getAllUsers();
    void uploadImage(File uploadedFile, User loggedUser);
    Blob retrieveImage(User loggedUser);
}