package services;

import beans.User;
import daos.PersistException;
import daos.UserDAO;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.sql.Blob;
import java.util.List;

@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    public UserServiceImpl(MySQLUserDAOImpl mySQLUserDAO) {
        this.userDAO = mySQLUserDAO;
    }

    public UserServiceImpl() {
    }

    @Override
    public User addUser(User user) {
        try {
            return userDAO.persist(user);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public User getUserByID(Integer id) {
        try {
            return userDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public User getUserByName(String name) {
        try {
            return userDAO.getUserByName(name);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userDAO.update(user);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            userDAO.delete(user);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public void uploadImage(File uploadedFile, User loggedUser) {
        try {
            userDAO.addImageToDB(uploadedFile, loggedUser);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    @Override
    public Blob retrieveImage(User loggedUser) {
        return userDAO.getImageFromDB(loggedUser);
    }

}