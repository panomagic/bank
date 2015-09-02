package mysql;

import beans.Account;
import beans.Transaction;
import daos.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MySQLTransactionDAOImpl extends AbstractJDBCDAO<Transaction, Integer> implements TransactionDAO {
    private static final Logger logger = Logger.getLogger(MySQLTransactionDAOImpl.class);

    private class PersistTransaction extends Transaction {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM transactions";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO transactions (currencies_currencyID, " +
                "clients_payerID, accounts_payerAccID, clients_recipientID, accounts_recipientAccID, " +
                "transactionTypes_transTypeID, sum) VALUES(?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return null;    //transaction cannot be updated
    }

    @Override
    public String getDeleteQuery() {
        return null;   //transaction cannot be deleted
    }

    public Transaction create() throws PersistException {
        Transaction transaction = new Transaction();
        return persist(transaction);
    }

    public Transaction getByPK(int key) throws SQLException {
        return null;
    }

    public MySQLTransactionDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Transaction> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Transaction> result = new LinkedList<Transaction>();
        try {
            while (rs.next()) {
                PersistTransaction transaction = new PersistTransaction();
                transaction.setid(rs.getInt("id"));
                transaction.setCurrencyID(rs.getInt("currencies_currencyID"));
                transaction.setPayerID(rs.getInt("clients_payerID"));
                transaction.setPayerAccID(rs.getInt("accounts_payerAccID"));
                transaction.setRecipientID(rs.getInt("clients_recipientID"));
                transaction.setRecipientAccID(rs.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(rs.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(rs.getTimestamp("trans_datetime"));
                transaction.setSum(rs.getBigDecimal("sum"));
                result.add(transaction);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Transaction object) throws PersistException {
        //declared in AbstractJDBCDAO
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Transaction object) throws PersistException {
        //declared in AbstractJDBCDAO
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Transaction object) throws PersistException {
        //declared in AbstractJDBCDAO
    }

    public void addTransaction(Transaction transaction) throws PersistException {

        PreparedStatement preparedStatement = null;

        Account payerAcc = null;
        Account recipientAcc = null;

        MySQLDAOFactory factory = new MySQLDAOFactory();

        try {
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            payerAcc = (Account) dao.getByPK(transaction.getPayerAccID());
            Connection connection2 = factory.getContext();
            GenericDAO dao2 = factory.getDAO(connection2, Account.class);
            recipientAcc = (Account) dao2.getByPK(transaction.getRecipientAccID());
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        Connection connection = factory.getContext();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO transactions (currencies_currencyID, " +
                    "clients_payerID, accounts_payerAccID, clients_recipientID, accounts_recipientAccID, " +
                    "transactionTypes_transTypeID, sum) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, transaction.getCurrencyID());
            preparedStatement.setInt(2, transaction.getPayerID());
            preparedStatement.setInt(3, transaction.getPayerAccID());
            preparedStatement.setInt(4, transaction.getRecipientID());
            preparedStatement.setInt(5, transaction.getRecipientAccID());
            preparedStatement.setInt(6, transaction.getTransTypeID());
            preparedStatement.setBigDecimal(7, transaction.getSum());
            connection.setAutoCommit(false);    //auto commit mode off
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE id = ?");
            preparedStatement.setBigDecimal(1, payerAcc.getBalance().subtract(transaction.getSum()));
            preparedStatement.setInt(2, transaction.getPayerAccID());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE id = ?");
            preparedStatement.setBigDecimal(1, recipientAcc.getBalance().add(transaction.getSum()));
            preparedStatement.setInt(2, transaction.getRecipientAccID());
            preparedStatement.execute();

            connection.commit();    //finishing transaction by synchronous saving the transfer and changing account balances

            logger.info("New money transfer from account with id " + payerAcc.getid() +
                    " to account with с id " + recipientAcc.getid());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException excep) {
                    logger.error("MySQL DB error", excep);
                }
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.warn("Cannot change autoCommit mode", e);
            }

            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Cannot close prepared statement", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection", e);
                }
            }
        }
    }
}