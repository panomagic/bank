package services;

import beans.Currency;
import daos.PersistException;
import mysql.MySQLCurrencyDAOImpl;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class);

    MySQLCurrencyDAOImpl mySQLCurrencyDAO;

    public CurrencyServiceImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        mySQLCurrencyDAO = new MySQLCurrencyDAOImpl(connection);
    }

    @Override
    public Currency addCurrency(Currency currency) {
        try {
            return mySQLCurrencyDAO.persist(currency);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Currency getCurrencyByID(Integer id) {
        try {
            return mySQLCurrencyDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateCurrency(Currency currency) {
        try {
            mySQLCurrencyDAO.update(currency);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteCurrency(Currency currency) {
        try {
            mySQLCurrencyDAO.delete(currency);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Currency> getAllCurrencies() {
        try {
            return mySQLCurrencyDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}