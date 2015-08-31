package daos;

import beans.Transaction;

import java.sql.SQLException;

/**
 * List of special methods for TransactionDAO
 */
public interface TransactionDAO {
    void addTransaction(Transaction transaction) throws PersistException;
}
