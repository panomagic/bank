package services;

import beans.User;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;
import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static MySQLUserDAOImpl getUserDaoImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return new MySQLUserDAOImpl(connection);
    }

    MySQLUserDAOImpl mySQLUserDAO = getUserDaoImpl();

    @Override
    public User addUser(User user) {
        try {
            return mySQLUserDAO.persist(user);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public User getUserByID(Integer id) {
        try {
            return mySQLUserDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            mySQLUserDAO.update(user);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            mySQLUserDAO.delete(user);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return mySQLUserDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    public void uploadImage(File uploadedFile, User loggedUser) {
        try {
            mySQLUserDAO.addImageToDB(uploadedFile, loggedUser);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    public Blob retrieveImage(User loggedUser) {
        return mySQLUserDAO.getImageFromDB(loggedUser);
    }
}