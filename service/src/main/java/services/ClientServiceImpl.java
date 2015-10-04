package services;

import beans.Client;
import daos.PersistException;
import mysql.MySQLClientDAOImpl;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);

    private static MySQLClientDAOImpl getClientDaoImpl() {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return new MySQLClientDAOImpl(connection);
    }

    @Override
    public Client addClient(Client client) {
        MySQLClientDAOImpl mySQLClientDAO = getClientDaoImpl();
        try {
            return mySQLClientDAO.persist(client);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Client getClientByID(Integer id) {
        MySQLClientDAOImpl mySQLClientDAO = getClientDaoImpl();
        try {
            return mySQLClientDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateClient(Client client) {
        MySQLClientDAOImpl mySQLClientDAO = getClientDaoImpl();
        try {
            mySQLClientDAO.update(client);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteClient(Client client) {
        MySQLClientDAOImpl mySQLClientDAO = getClientDaoImpl();
        try {
            mySQLClientDAO.delete(client);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Client> getAllClients() {
        MySQLClientDAOImpl mySQLClientDAO = getClientDaoImpl();
        try {
            return mySQLClientDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}