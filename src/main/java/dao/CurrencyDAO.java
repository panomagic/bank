package dao;

import bean.Currency;
import service.Management;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyDAO {
    public Currency getCurrencyByID(int currencyID) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Currency currency = new Currency();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM currencies WHERE currencyID = ?");
            preparedStatement.setInt(1, currencyID);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                currency.setCurrencyID(resultSet.getInt("currencyID"));
                currency.setCurrency(resultSet.getString("currency"));
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
        return currency;
    }
}
