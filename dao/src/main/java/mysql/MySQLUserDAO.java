package mysql;

import beans.Role;
import beans.User;
import daos.AbstractJDBCDAO;
import daos.PersistException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLUserDAO extends AbstractJDBCDAO<User, Integer> {
    private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);

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

    public void addImage(String imgUploadPath, User object) throws SQLException, IOException {
        PreparedStatement statement = null;
        FileInputStream fis = null;
        MySQLDAOFactory factory;
        Connection connection = null;

        try {
            factory = new MySQLDAOFactory();
            connection = factory.getContext();
            File file = new File(imgUploadPath);
            fis = new FileInputStream(file);
            if (file.length() > 102400) {
                System.out.println(file.length());
                statement = connection.prepareStatement("UPDATE users SET imagepath=? WHERE id=?");
                statement.setString(1, imgUploadPath);  //менять название файла на id.jpg
                statement.setInt(2, object.getid());
                statement.executeUpdate();
            } else {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement("UPDATE users SET image=? WHERE id=?");
                statement.setBinaryStream(1, fis, (int) file.length());
                statement.setInt(2, object.getid());
                statement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PersistException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            statement.close();
            fis.close();
            if (connection != null) {
                connection.close();
                logger.info("DB connection is closed");
            }
        }
    }
}