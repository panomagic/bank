package daos;

import beans.Identified;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *  Abstract class that represents the base realization of CRUD operations using JDBC
 * @param <T> persistence object type
 * @param <PK> primary key type
 */
public abstract class AbstractJDBCDAO<T extends Identified<PK>, PK extends Integer> implements GenericDAO<T, PK> {

    private Connection connection;
    private static final Logger logger = Logger.getLogger(AbstractJDBCDAO.class);


    /**
     * Returns SQL query for all records from DB table
     * SELECT * from [Table]
     */
    public abstract String getSelectQuery();

    /**
     * Returns SQL query for inserting a new record to the DB table
     * INSERT INTO [Table] ([column1, column2, ...] values (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Returns SQL query for updating a record of the DB table
     * UPDATE [Table] SET [column1 = ?, column2 = ?, ...] WHERE id = ?;
     */
    public abstract String getUpdateQuery();

    /**
     * Returns SQL query for deleting a record of the DB table
     * DELETE FROM [Table] WHERE id = ?;
     */
    public abstract String getDeleteQuery();

    /**
     * Parses ResultSet and returns list of its objects
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    /**
     * Sets arguments of INSERT query according to object argument values
     */
    public abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    /**
     * Sets arguments of UPDATE query according to object argument values
     */
    public abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    /**
     * Sets arguments of DELETE query according to object argument values
     */
    public abstract void prepareStatementForDelete(PreparedStatement statement, T object) throws PersistException;

    public T getByPK(Integer key) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection");
                }
            }
        }

        if ((list == null) || (list.size() == 0))
            return null;
        if (list.size() > 1)
            throw new PersistException("Received more than one record");

        return list.iterator().next();
    }

    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        }
        catch (Exception e) {
            throw new PersistException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection");
                }
            }
        }
        return list;
    }

    public T persist(T object) throws PersistException {    //method to create a record about an object
        if (object.getid() != null)
            throw new PersistException("Object is already persist");
        T persistInstance;

        //adding record
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1)
                throw new PersistException("On persist modify more than 1 record: " + count);
        } catch (Exception e) {
            throw new PersistException(e);
        }

        //receiving just added record
        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1))
                throw new PersistException("Exception in findByPK new persist data");
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection");
                }
            }
        }
        return persistInstance;
    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1)
                throw new PersistException("On update modify more than 1 record: " + count);
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection");
                }
            }
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getid());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1)
                throw new PersistException("On delete modify more than 1 record: " + count);
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection");
                }
            }
        }
    }

    public AbstractJDBCDAO(Connection connection) {
        this.connection = connection;
    }
}