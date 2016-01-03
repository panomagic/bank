package services;

import beans.User;
import daos.PersistException;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.sql.Blob;
import java.util.List;

@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    MySQLUserDAOImpl mySQLUserDAO;

    @Autowired
    public UserServiceImpl(MySQLUserDAOImpl mySQLUserDAO) {
        this.mySQLUserDAO = mySQLUserDAO;
    }

    public UserServiceImpl() {
    }

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

    @Override
    public void uploadImage(File uploadedFile, User loggedUser) {
        try {
            mySQLUserDAO.addImageToDB(uploadedFile, loggedUser);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    @Override
    public Blob retrieveImage(User loggedUser) {
        return mySQLUserDAO.getImageFromDB(loggedUser);
    }

    @Override
    public void sendEmailToUser(User user) {
        try {
            mySQLUserDAO.generateAndSendEmail(user);
        } catch (MessagingException e) {
            logger.error("Error during sending email", e);
        }
    }
}