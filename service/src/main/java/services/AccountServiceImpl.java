package services;

import beans.Account;
import daos.PersistException;
import mysql.MySQLAccountDAOImpl;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    private static MySQLAccountDAOImpl getAccountDaoImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return new MySQLAccountDAOImpl(connection);
    }

    MySQLAccountDAOImpl mySQLAccountDAO = getAccountDaoImpl();

    @Override
    public Account addAccount(Account account) {
        try {
            return mySQLAccountDAO.persist(account);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Account getAccountByID(Integer id) {
        try {
            return mySQLAccountDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            mySQLAccountDAO.update(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try {
            mySQLAccountDAO.delete(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            return mySQLAccountDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}