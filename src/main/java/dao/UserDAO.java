package dao;

import bean.Role;
import bean.User;
import service.Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List getAllUsers() throws SQLException {
        List allUsers = new ArrayList();

        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM users");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.fromString(resultSet.getString("role")));
                user.setClientID(resultSet.getInt("clients_clientID"));
                allUsers.add(user);
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
        return allUsers;
    }

    public User getUserByUserName(String userName) throws SQLException {
        Connection connection = Management.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE userName = ?");
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.fromString(resultSet.getString("role")));
                user.setClientID(resultSet.getInt("clients_clientID"));
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
        return user;
    }
}