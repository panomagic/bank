package mysql;

import beans.*;
import daos.DAOFactory;
import daos.GenericDAO;
import daos.PersistException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLDAOFactory implements DAOFactory<Connection> {

    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    //private static final String URL = "jdbc:mysql://127.6.230.130:3306/bank";
    private static final String USERNAME = "root";
    private static final String PSW = "970195";
    private final String driver = "com.mysql.jdbc.Driver";
    private Map<Class, DAOCreator> creators;

    private static final Logger logger = Logger.getLogger(MySQLDAOFactory.class);

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PSW);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        logger.info("DB connection is established");
        return connection;
    }

    @Override
    public GenericDAO getDAO(Connection connection, Class dtoClass) throws PersistException {
        DAOCreator creator = creators.get(dtoClass);
        if (creator == null)
            throw new PersistException("DAO object for " + dtoClass + " not found");
        return creator.create(connection);
    }

    public MySQLDAOFactory() {
        try {
            Class.forName(driver);  //driver registration
        }
        catch (ClassNotFoundException e) {
            logger.error("Tomcat is unable to connect to DB", e);
        }

        creators = new HashMap<Class, DAOCreator>();
        creators.put(Account.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLAccountDAOImpl(connection);
            }
        });

        creators.put(Client.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLClientDAOImpl(connection);
            }
        });

        creators.put(Currency.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLCurrencyDAOImpl(connection);
            }
        });

        creators.put(Transaction.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLTransactionDAOImpl(connection);
            }
        });

        creators.put(User.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLUserDAOImpl(connection);
            }
        });
    }
}