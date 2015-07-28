package daos;

import beans.*;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CurrencyDAO {
    private static final Logger logger = Logger.getLogger(CurrencyDAO.class);

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
        return currency;
    }

    public List getAllCurrencies() throws SQLException {
        List currenciesList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM currencies");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setCurrencyID(resultSet.getInt("currencyID"));
                currency.setCurrency(resultSet.getString("currency"));
                currenciesList.add(currency);
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
        return currenciesList;
    }
}