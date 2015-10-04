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

    private static MySQLCurrencyDAOImpl getCurrencyDaoImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return new MySQLCurrencyDAOImpl(connection);
    }

    @Override
    public Currency addCurrency(Currency currency) {
        MySQLCurrencyDAOImpl mySQLCurrencyDAO = getCurrencyDaoImpl();
        try {
            return mySQLCurrencyDAO.persist(currency);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Currency getCurrencyByID(Integer id) {
        MySQLCurrencyDAOImpl mySQLCurrencyDAO = getCurrencyDaoImpl();
        try {
            return mySQLCurrencyDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateCurrency(Currency currency) {
        MySQLCurrencyDAOImpl mySQLCurrencyDAO = getCurrencyDaoImpl();
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
        MySQLCurrencyDAOImpl mySQLCurrencyDAO = getCurrencyDaoImpl();
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
        MySQLCurrencyDAOImpl mySQLCurrencyDAO = getCurrencyDaoImpl();
        try {
            return mySQLCurrencyDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}