package mysql;

import beans.*;
import daos.DAOFactory;
import daos.GenericDAO;
import daos.PersistException;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Transactional
public class MySQLDAOFactory implements DAOFactory<Connection> {

    //private static final String URL = "jdbc:mysql://127.6.230.130:3306/bank"; //openshift MySQLDB settings

    //private static final String URL = "jdbc:mysql://localhost:3306/bank";     //old settings before dataSource
    //private static final String USERNAME = "root";                            //old settings before dataSource
    //private static final String PSW = "970195";                               //old settings before dataSource

    private final String driver = "com.mysql.jdbc.Driver";
    private Map<Class, DAOCreator> creators;

    private static final Logger logger = Logger.getLogger(MySQLDAOFactory.class);

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public GenericDAO getDAO(Connection connection, Class dtoClass) throws PersistException {
        DAOCreator creator = creators.get(dtoClass);
        if (creator == null)
            throw new PersistException("DAO object for " + dtoClass + " not found");
        return creator.create(connection);
    }

    public void initMysqlDaoFactory() {
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
                return new MySQLAccountDAOImpl(dataSource);
            }
        });

        creators.put(Client.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLClientDAOImpl(dataSource);
            }
        });

        creators.put(Currency.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLCurrencyDAOImpl(dataSource);
            }
        });

        creators.put(Transaction.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLTransactionDAOImpl(dataSource);
            }
        });

        creators.put(User.class, new DAOCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySQLUserDAOImpl(dataSource);
            }
        });
    }

    public void destroyMysqlDaoFactory() {
        logger.info("MysqlDaoFactory was destroyed");
    }
}