package services;

import beans.Client;

import java.util.List;

public interface ClientService {
    Client addClient(Client client);
    Client getClientByID(Integer id);
    boolean updateClient(Client client);
    boolean deleteClient(Client client);
    List<Client> getAllClients();
}
