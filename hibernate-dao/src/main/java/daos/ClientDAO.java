package daos;

import entity.Client;

import java.util.List;

public interface ClientDAO {
    Client getClientById(Integer id);
    List getAllClients();
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
}