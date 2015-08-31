package daos;

import beans.User;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * List of special methods for UserDAO
 */
public interface UserDAO {
    void addImageToDB(File uploadedFile, User user) throws Exception;

    Blob getImageFromDB(User loggedUser);
}
