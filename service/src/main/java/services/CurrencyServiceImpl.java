package services;

import beans.Currency;
import daos.PersistException;
import mysql.MySQLCurrencyDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("currencyService")
@Scope("prototype")
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class);

    @Autowired
    MySQLCurrencyDAOImpl mySQLCurrencyDAO;

    @Autowired
    public CurrencyServiceImpl(MySQLCurrencyDAOImpl mySQLCurrencyDAO) {
        this.mySQLCurrencyDAO = mySQLCurrencyDAO;
    }

    public CurrencyServiceImpl() {
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