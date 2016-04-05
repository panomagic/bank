package impl;

import daos.ClientDAO;
import entity.Client;
import org.hibernate.Session;
import persistence.HibernateUtil;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    @Override
    public Client getClientById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = (Client) session.get(Client.class, id);
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Client> clientList = (List<Client>) session.createCriteria(Client.class).list();
        session.close();
        return clientList;
    }

    @Override
    public void addClient(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateClient(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(client);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteClient(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(client);
        session.getTransaction().commit();
        session.close();
    }
}