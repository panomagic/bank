package mysql;

import beans.Account;
import beans.Transaction;
import daos.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MySQLTransactionDAO extends AbstractJDBCDAO<Transaction, Integer> {
    private static final Logger logger = Logger.getLogger(MySQLTransactionDAO.class);

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

    public MySQLTransactionDAO(Connection connection) {
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

    //use addTransaction method instead due to 
    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Transaction object) throws PersistException {
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Transaction object) throws PersistException {
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Transaction object) throws PersistException {
    }

    public void addTransaction(Transaction transaction) throws SQLException {

        PreparedStatement preparedStatementTransAct = null;
        ResultSet resultSetPayer = null;
        PreparedStatement psGetPayerAcc = null;
        PreparedStatement psUpdatePayerBalance = null;
        ResultSet resultSetRec = null;
        PreparedStatement psGetRecipientAcc = null;
        PreparedStatement psUpdateRecipientBalance = null;

        MySQLDAOFactory factory;
        Connection connection = null;
        Account payerAcc = null;
        Account recipientAcc = null;

        try {
            factory = new MySQLDAOFactory();
            connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            payerAcc = (Account) dao.getByPK(transaction.getPayerAccID());
            recipientAcc = (Account) dao.getByPK(transaction.getRecipientAccID());
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        try {
            preparedStatementTransAct = connection.prepareStatement("INSERT INTO transactions (currencies_currencyID, " +
                    "clients_payerID, accounts_payerAccID, clients_recipientID, accounts_recipientAccID, " +
                    "transactionTypes_transTypeID, sum) VALUES(?,?,?,?,?,?,?)");
            preparedStatementTransAct.setInt(1, transaction.getCurrencyID());
            preparedStatementTransAct.setInt(2, transaction.getPayerID());
            preparedStatementTransAct.setInt(3, transaction.getPayerAccID());
            preparedStatementTransAct.setInt(4, transaction.getRecipientID());
            preparedStatementTransAct.setInt(5, transaction.getRecipientAccID());
            preparedStatementTransAct.setInt(6, transaction.getTransTypeID());
            preparedStatementTransAct.setBigDecimal(7, transaction.getSum());


            //updating payer's balance:
            psUpdatePayerBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE id = ?");
            psUpdatePayerBalance.setBigDecimal(1, payerAcc.getBalance().subtract(transaction.getSum()));
            psUpdatePayerBalance.setInt(2, transaction.getPayerAccID());

            //updating recipient's balance:
            psUpdateRecipientBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE id = ?");
            psUpdateRecipientBalance.setBigDecimal(1, recipientAcc.getBalance().add(transaction.getSum()));
            psUpdateRecipientBalance.setInt(2, transaction.getRecipientAccID());

            connection.setAutoCommit(false);    //auto commit mode off

            preparedStatementTransAct.execute();
            psUpdatePayerBalance.execute();
            psUpdateRecipientBalance.execute();

            connection.commit();    //finishing transaction by synchronous saving the transfer and changing account balances

            logger.info("New money transfer from account with id " + payerAcc.getid() +
                    " to account with с id " + recipientAcc.getid());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException excep) {
                    logger.warn("MySQL DB error", excep);
                }
            }
        }
        finally {
            if(resultSetPayer != null)
                resultSetPayer.close();

            if(resultSetRec != null)
                resultSetRec.close();

            if(preparedStatementTransAct != null)
                preparedStatementTransAct.close();

            if(psGetPayerAcc != null)
                psGetPayerAcc.close();

            if(psUpdatePayerBalance != null)
                psUpdatePayerBalance.close();

            if(psGetRecipientAcc != null)
                psGetRecipientAcc.close();

            if(psUpdateRecipientBalance != null)
                psUpdateRecipientBalance.close();

            connection.setAutoCommit(true);

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
    }
}