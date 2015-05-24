package dao;

import bean.*;
import service.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    public void addClient(Client client) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO clients (fullName, gender, dateOfBirth, dateOfReg) VALUES(?,?,?,?)");
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setString(2, client.getGender().genderAsChar());
            preparedStatement.setDate(3, new Date(client.getDateOfBirth().getTime()));
            preparedStatement.setDate(4, new Date(client.getDateOfReg().getTime()));

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

    public void deleteClient(Client client) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE clientID = ?");
            preparedStatement.setInt(1, client.getClientID());

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

    public List getAllClients() throws SQLException {
        List clientsList = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM clients");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setClientID(resultSet.getInt("clientID"));
                client.setFullName(resultSet.getString("fullName"));
                client.setGender(Gender.fromString(resultSet.getString("gender")));
                client.setDateOfBirth(resultSet.getDate("dateOfBirth"));
                client.setDateOfReg(resultSet.getDate("dateOfReg"));
                clientsList.add(client);
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
        return clientsList;
    }
}