package services;

import beans.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ClientService {
    Client addClient(Client client);
    Client getClientByID(Integer id);
    boolean updateClient(Client client);
    boolean deleteClient(Client client);
    List<Client> getAllClients();
}