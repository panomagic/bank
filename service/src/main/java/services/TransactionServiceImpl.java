package services;

import beans.Transaction;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import mysql.MySQLTransactionDAOImpl;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    private static MySQLTransactionDAOImpl getTransactionDaoImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return new MySQLTransactionDAOImpl(connection);
    }

    @Override
    public void addTransactionService(Transaction transaction) {
        MySQLTransactionDAOImpl mySQLTransactionDAO = getTransactionDaoImpl();
        try {
            mySQLTransactionDAO.addTransaction(transaction);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    @Override
    public Transaction getTransactionByID(Integer id) {
        MySQLTransactionDAOImpl mySQLTransactionDAO = getTransactionDaoImpl();
        try {
            return mySQLTransactionDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {     //todo maybe REMOVE because of unnecessary
        MySQLTransactionDAOImpl mySQLTransactionDAO = getTransactionDaoImpl();
        try {
            mySQLTransactionDAO.update(transaction);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {     //todo maybe REMOVE because of unnecessary
        MySQLTransactionDAOImpl mySQLTransactionDAO = getTransactionDaoImpl();
        try {
            mySQLTransactionDAO.delete(transaction);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        MySQLTransactionDAOImpl mySQLTransactionDAO = getTransactionDaoImpl();
        try {
            return mySQLTransactionDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}