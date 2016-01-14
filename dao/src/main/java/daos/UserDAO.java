package daos;

import beans.User;

import java.io.File;
import java.sql.Blob;

/**
 * List of special methods for UserDAO
 */
public interface UserDAO {
    void addImageToDB(File uploadedFile, User user) throws PersistException;

    Blob getImageFromDB(User loggedUser);
}