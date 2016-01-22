package services;

import beans.Client;
import daos.ClientDAO;
import daos.PersistException;
import mysql.MySQLClientDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
@Scope("prototype")
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    public ClientServiceImpl(MySQLClientDAOImpl mySQLClientDAO) {
        this.clientDAO = mySQLClientDAO;
    }

    public ClientServiceImpl() {
    }


    @Override
    public Client addClient(Client client) {
        try {
            return clientDAO.persist(client);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Client getClientByID(Integer id) {
        try {
            return clientDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateClient(Client client) {
        try {
            clientDAO.update(client);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteClient(Client client) {
        try {
            clientDAO.delete(client);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Client> getAllClients() {
        try {
            return clientDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}