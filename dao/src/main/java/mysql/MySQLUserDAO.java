package mysql;

import beans.Role;
import beans.User;
import daos.AbstractJDBCDAO;
import daos.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLUserDAO extends AbstractJDBCDAO<User, Integer> {

    private class PersistUser extends User {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM users";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (userName, " +
                "password, role, clients_clientID) VALUES(?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET userName=?, " +
                "password=?, role=?, clients_clientID=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id=?";
    }

    public User create() throws PersistException {
        User user = new User();
        return persist(user);
    }

    public User getByPK(int key) throws SQLException {
        return null;
    }

    public MySQLUserDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setid(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.fromString(rs.getString("role")));
                user.setClientID(rs.getInt("clients_clientID"));
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getUserName());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getRole().roleAsChar());
            statement.setInt(4, object.getClientID());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getUserName());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getRole().roleAsChar());
            statement.setInt(4, object.getClientID());
            statement.setInt(5, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setInt(1, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}