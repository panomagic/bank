package client.dao;

import beans.Client;
import java.util.List;

public interface HSQLClientDAO {

    void persistClient(Client client);

    void updateClient(Client client);

    void deleteClient(Client client);

    Client getClientByPK(Integer id);

    List<Client> getAllClients();
}