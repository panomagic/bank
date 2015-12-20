package mysql;

import beans.Role;
import beans.User;
import daos.AbstractJDBCDAO;
import daos.GenericDAO;
import daos.PersistException;
import daos.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository("mySQLUserDAO")
@Scope("prototype")
public class MySQLUserDAOImpl extends AbstractJDBCDAO<User, Integer> implements UserDAO {
    private static final Logger logger = Logger.getLogger(MySQLUserDAOImpl.class);

    private class PersistUser extends User {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Autowired
    public MySQLUserDAOImpl(DataSource dataSource) {
        super(dataSource);
        logger.info("MySQLUserDAOImpl created");
    }

    @Autowired
    MySQLDAOFactory mySQLDAOFactory;

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM users";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (userName, " +
                "psw, role, clients_clientID) VALUES(?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET userName=?, " +
                "psw=?, role=?, clients_clientID=? WHERE id=?";
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

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setid(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setPsw(rs.getString("psw"));
                user.setRole(Role.fromString(rs.getString("role")));
                user.setClientID(rs.getInt("clients_clientID"));
                user.setImage(rs.getBlob("image"));
                user.setImagepath(rs.getString("imagepath"));
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
            statement.setString(2, object.getPsw());
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
            statement.setString(2, object.getPsw());
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

    public void addImageToDB(File uploadedFile, User user) throws PersistException {
        PreparedStatement statement = null;
        FileInputStream fis = null;
        Connection connection = null;

        try {
            connection = getDataSource().getConnection();

            fis = new FileInputStream(uploadedFile);

            if (uploadedFile.length() > 102400) {   //if an image is >100 kb saving it to the file system
                statement = connection.prepareStatement("UPDATE users SET image=?, imagepath=? WHERE id=?");
                statement.setBinaryStream(1, null); //erasing blob image in DB to avoid conflicts
                statement.setString(2, uploadedFile.getPath());
                statement.setInt(3, user.getid());
                statement.executeUpdate();
            } else {                                //if an image is <100 kb saving it to the DB
                connection.setAutoCommit(false);
                statement = connection.prepareStatement("UPDATE users SET image=?, imagepath=? WHERE id=?");
                statement.setBinaryStream(1, fis, (int) uploadedFile.length());
                statement.setString(2, null);       //erasing image path to avoid conflicts
                statement.setInt(3, user.getid());
                statement.executeUpdate();
                connection.commit();

                fis.close();
                Files.delete(Paths.get(uploadedFile.getPath()));
            }
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
            try {
                connection.rollback();
            } catch (SQLException excep) {
                logger.error("MySQL DB error", excep);
            }
        }
        catch (FileNotFoundException e) {
            logger.error("MySQL DB error", e);
        } catch (IOException exception) {
            logger.error("IO error", exception);
        }
        finally {
            try {
                connection.setAutoCommit(true);
                statement.close();
                fis.close();
                if (connection != null) {
                    connection.close();
                    logger.info("DB connection is closed");
                }
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            } catch (IOException exc) {
                logger.error("IO error", exc);
            }
        }
    }

    public Blob getImageFromDB(User loggedUser) {
        GenericDAO daoUser;

        User user = null;
        try {
            Connection connection = getDataSource().getConnection();
            daoUser = mySQLDAOFactory.getDAO(connection, User.class);
            user = (User) daoUser.getByPK(loggedUser.getid());
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        return user.getImage();
    }
}