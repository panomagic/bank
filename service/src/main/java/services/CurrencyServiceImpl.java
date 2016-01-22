package services;

import beans.Currency;
import daos.CurrencyDAO;
import daos.PersistException;
import mysql.MySQLCurrencyDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("currencyService")
@Scope("prototype")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class);

    @Autowired
    CurrencyDAO currencyDAO;

    @Autowired
    public CurrencyServiceImpl(MySQLCurrencyDAOImpl mySQLCurrencyDAO) {
        this.currencyDAO = mySQLCurrencyDAO;
    }

    public CurrencyServiceImpl() {
    }

    @Override
    public Currency addCurrency(Currency currency) {
        try {
            return currencyDAO.persist(currency);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Currency getCurrencyByID(Integer id) {
        try {
            return currencyDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateCurrency(Currency currency) {
        try {
            currencyDAO.update(currency);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteCurrency(Currency currency) {
        try {
            currencyDAO.delete(currency);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Currency> getAllCurrencies() {
        try {
            return currencyDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}