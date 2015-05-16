package dao;

import bean.*;
import service.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
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
        return account;
    }

    public void deleteAccount(Account account) throws SQLException {
        Connection connection = Management.getDBConnection();

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE accountID = ?");
            preparedStatement.setInt(1, account.getAccountID());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
    }
}
