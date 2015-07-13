package dao;

import bean.*;
import service.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.*;

public class AccountDAO {
    private static final Logger logger = Logger.getLogger(AccountDAO.class);

    public void addAccount(Account account) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO accounts (clients_clientID, " +
                    "currencies_currencyID, accountTypes_accTypeID) VALUES(?,?,?)");
            preparedStatement.setInt(1, account.getClientID()); //где 1 - порядковый номер параметра (?) в запросе
            preparedStatement.setInt(2, account.getCurrencyID());
            preparedStatement.setInt(3, account.getAccTypeID());

            preparedStatement.execute();

            logger.info("New account was added for client with id " + account.getClientID());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
    }

    public void updateAccount(Account account) throws SQLException {    //ДОДЕЛАТЬ МЕТОД
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE accounts SET clients_clientID=?, currencies_currencyID=?, accountTypes_accTypeID=? WHERE accountID=?");
            preparedStatement.setInt(1, account.getClientID());
            preparedStatement.setInt(2, account.getCurrencyID());
            preparedStatement.setInt(3, account.getAccTypeID());
            preparedStatement.setInt(4, account.getAccountID());

            preparedStatement.execute();

            logger.info("Account with id " + account.getAccountID() + " was updated");
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
    }

    public Account getAccountByID(int accID) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE accountID = ?");
            preparedStatement.setInt(1, accID);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                account.setAccountID(resultSet.getInt("accountID"));
                account.setClientID(resultSet.getInt("clients_clientID"));
                account.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                account.setAccTypeID(resultSet.getInt("accountTypes_accTypeID"));
                account.setBalance(resultSet.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
        return account;
    }


    //получение списка счетов конкретного клиента
    public List getAccountsByClientID(int clientID) throws SQLException {
        List accountsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE clients_clientID=?");
            preparedStatement.setInt(1, clientID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountID(resultSet.getInt("accountID"));
                account.setClientID(resultSet.getInt("clients_clientID"));
                account.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                account.setAccTypeID(resultSet.getInt("accountTypes_accTypeID"));
                account.setBalance(resultSet.getBigDecimal("balance"));
                accountsList.add(account);
            }
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
        return accountsList;
    }

    //получение списка всех счетов
    public List getAllAccounts() throws SQLException {
        List accountsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM accounts");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountID(resultSet.getInt("accountID"));
                account.setClientID(resultSet.getInt("clients_clientID"));
                account.setCurrencyID(resultSet.getInt("currencies_currencyID"));
                account.setAccTypeID(resultSet.getInt("accountTypes_accTypeID"));
                account.setBalance(resultSet.getBigDecimal("balance"));
                accountsList.add(account);
            }
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (resultSet != null)
                resultSet.close();

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
        return accountsList;
    }

    public void deleteAccount(Account account) throws SQLException {
        Connection connection = Management.getDBConnection();

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE accountID = ?");
            preparedStatement.setInt(1, account.getAccountID());

            preparedStatement.execute();

            logger.info("Account with id " + account.getAccountID() + " was deleted");
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
    }
}