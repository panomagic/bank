package dao;

import bean.*;
import service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void addTransaction(Transaction transaction) throws SQLException {
        Connection connection = Management.getDBConnection();

        PreparedStatement preparedStatementTransAct = null;
        ResultSet resultSetPayer = null;
        PreparedStatement psGetPayerAcc = null;
        PreparedStatement psUpdatePayerBalance = null;
        ResultSet resultSetRec = null;
        PreparedStatement psGetRecipientAcc = null;
        PreparedStatement psUpdateRecipientBalance = null;

        Account payerAcc = new AccountDAO().getAccountByID(transaction.getPayerAccID());
        Account recipientAcc = new AccountDAO().getAccountByID(transaction.getRecipientAccID());

        //проверка для дебитного счета плательщика (сумма транзакции не больше баланса):
        if (payerAcc.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAcc.getBalance()) == 1) {
            System.out.println("Недостаточно средств на счету плательщика!");
            return;
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


            //обновляем баланс счета плательщика (сам счет уже получен):
            psUpdatePayerBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE accountID = ?");
            psUpdatePayerBalance.setBigDecimal(1, payerAcc.getBalance().subtract(transaction.getSum()));
            psUpdatePayerBalance.setInt(2, transaction.getPayerAccID());

            //обновляем баланс счета получателя (сам счет уже получен):
            psUpdateRecipientBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE accountID = ?");
            psUpdateRecipientBalance.setBigDecimal(1, recipientAcc.getBalance().add(transaction.getSum()));
            psUpdateRecipientBalance.setInt(2, transaction.getRecipientAccID());

            connection.setAutoCommit(false);    //отключаем режим автоматической фиксации изменений

            preparedStatementTransAct.execute();
            psUpdatePayerBalance.execute();
            psUpdateRecipientBalance.execute();

            connection.commit();                //завершаем транзакцию, одновременно проводя перевод и изменяя баланс на счетах

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.print("Ошибка при проведении! Транзакция отменена");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
        finally {
            //проверяем корректность выполненной транзакции - ВРЕМЕННО ОСТАВЛЕНО, хотя с отключением autoCommit стало лишним
            Account payerAccNew = new AccountDAO().getAccountByID(transaction.getPayerAccID());
            BigDecimal payerBalanceNew = (payerAcc.getBalance().subtract(transaction.getSum())).setScale(2, RoundingMode.HALF_UP);
            Account recipientAccNew = new AccountDAO().getAccountByID(transaction.getRecipientAccID());
            BigDecimal recipientBalanceNew = (recipientAcc.getBalance().add(transaction.getSum())).setScale(2, RoundingMode.HALF_UP);
            if((payerBalanceNew.compareTo(payerAccNew.getBalance()) != 0) ||
                    (recipientBalanceNew.compareTo(recipientAccNew.getBalance())) != 0)
                throw new TransactionFailedException("Ошибка при выполнении транзакции! Проверьте баланс счетов");

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
                System.out.println("Соединение с БД закрыто");
            }
        }
    }

    public List getAllTransactions() throws SQLException {
        List transactionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transactionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transactionsList;
    }

    public List getTransactionsByPayerID(int payerID) throws SQLException {
        List transastionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE clients_payerID=?");
            preparedStatement.setInt(1, payerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transastionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transastionsList;
    }

    public List getTransactionsByRecipientID(int recipientID) throws SQLException {
        List transastionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE clients_recipientID=?");
            preparedStatement.setInt(1, recipientID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transastionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transastionsList;
    }

    public List getTransactionsByClientID(int clientID) throws SQLException {    //клиент или плательщик, или получатель
        List transastionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE clients_payerID=? OR clients_recipientID=?");
            preparedStatement.setInt(1, clientID);
            preparedStatement.setInt(2, clientID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transastionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transastionsList;
    }


    public List getTransactionsByPayerAccID(int payerAccID) throws SQLException {
        List transastionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE accounts_payerAccID=?");
            preparedStatement.setInt(1, payerAccID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transastionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transastionsList;
    }

    public List getTransactionsByRecipientAccID(int recipientAccID) throws SQLException {
        List transastionsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE accounts_recipientAccID=?");
            preparedStatement.setInt(1, recipientAccID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransID(resultSet.getInt("transID"));
                transaction.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                transaction.setPayerID(resultSet.getInt("clients_payerID"));
                transaction.setPayerAccID(resultSet.getInt("accounts_payerAccID"));
                transaction.setRecipientID(resultSet.getInt("clients_recipientID"));
                transaction.setRecipientAccID(resultSet.getInt("accounts_recipientAccID"));
                transaction.setTransTypeID(resultSet.getInt("transactionTypes_transTypeID"));
                transaction.setTransDateTime(resultSet.getTimestamp("trans_datetime"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transastionsList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
        return transastionsList;
    }
}