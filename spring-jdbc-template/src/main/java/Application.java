import account.dao.HSQLAccountDAOImpl;
import beans.Account;
import beans.Client;
import beans.Gender;
import client.dao.HSQLClientDAOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.text.SimpleDateFormat;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        /*ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "Spring-Module.xml");

        HSQLClientDAOImpl clientJdbcDAO = (HSQLClientDAOImpl) appContext.getBean("clientDao");

        //Getting ALL clients BEFORE updates
        List<Client> clientsList = clientJdbcDAO.getAllClients();
        System.out.println("Список клиентов ДО изменений:");
        for (int i = 0; i < clientsList.size(); i++) {
            System.out.println(clientsList.get(i).getid() + " | "
                    + clientsList.get(i).getFullName() + " | "
                    + clientsList.get(i).getGender() + " | "
                    + clientsList.get(i).getDateOfBirth() + " | "
                    + clientsList.get(i).getDateOfReg());
        }

        //Updating client
        Client updatedClient = new Client();
        updatedClient.setid(37);
        updatedClient.setFullName("Ященюк Василий");
        updatedClient.setGender(Gender.fromString("m"));
        updatedClient.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse("12.05.1992"));
        updatedClient.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse("12.05.2012"));
        clientJdbcDAO.updateClient(updatedClient);

        //Adding client
        Client newClient = new Client();
        newClient.setFullName("Ivanova Ivanna");
        newClient.setGender(Gender.FEMALE);
        newClient.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse("04.08.1990"));
        newClient.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse("08.11.2014"));
        clientJdbcDAO.persistClient(newClient);

        //Deleting client
        Client deletedClient = new Client();
        deletedClient.setid(33);
        clientJdbcDAO.deleteClient(deletedClient);

        //Getting ALL clients AFTER updates
        List<Client> updatedClientsList = clientJdbcDAO.getAllClients();
        System.out.println("Список клиентов ПОСЛЕ изменений:");
        for (int i = 0; i < updatedClientsList.size(); i++) {
            System.out.println(updatedClientsList.get(i).getid() + " | "
                    + updatedClientsList.get(i).getFullName() + " | "
                    + updatedClientsList.get(i).getGender() + " | "
                    + updatedClientsList.get(i).getDateOfBirth() + " | "
                    + updatedClientsList.get(i).getDateOfReg());
        }

        //Getting one of the clients
        Client selectedClient = clientJdbcDAO.getClientByPK(28);
        System.out.println("Проверка работы метода получения одного клиента:");
        System.out.println(selectedClient.getid() + " | "
                    + selectedClient.getFullName() + " | "
                    + selectedClient.getGender() + " | "
                    + selectedClient.getDateOfBirth() + " | "
                    + selectedClient.getDateOfReg()
        );


        HSQLAccountDAOImpl accountJdbcDAO = (HSQLAccountDAOImpl) appContext.getBean("accountDao");

        //Getting ALL accounts BEFORE updates
        List<Account> accountsList = accountJdbcDAO.getAllAccounts();
        System.out.println("Список счетов ДО изменений:");
        for (int i = 0; i < accountsList.size(); i++) {
            System.out.println(accountsList.get(i).getid() + " | "
                    + accountsList.get(i).getAccTypeID() + " | "
                    + accountsList.get(i).getCurrencyID() + " | "
                    + accountsList.get(i).getClientID() + " | "
                    + accountsList.get(i).getBalance());
        }

        //Updating account
        Account updatedAccount = new Account();
        updatedAccount.setid(8);
        updatedAccount.setCurrencyID(1);
        updatedAccount.setAccTypeID(1);
        updatedAccount.setClientID(17);
        accountJdbcDAO.updateAccount(updatedAccount);

        //Adding account
        Account newAccount = new Account();
        newAccount.setClientID(18);
        newAccount.setAccTypeID(2);
        newAccount.setCurrencyID(2);
        accountJdbcDAO.persistAccount(newAccount);

        //Deleting account
        Account deletedAccount = new Account();
        deletedAccount.setid(10);
        accountJdbcDAO.deleteAccount(deletedAccount);

        //Getting ALL accounts AFTER updates
        List<Account> updatedAccountsList = accountJdbcDAO.getAllAccounts();
        System.out.println("Список счетов ПОСЛЕ изменений:");
        for (int i = 0; i < updatedAccountsList.size(); i++) {
            System.out.println(updatedAccountsList.get(i).getid() + " | "
                    + updatedAccountsList.get(i).getAccTypeID() + " | "
                    + updatedAccountsList.get(i).getCurrencyID() + " | "
                    + updatedAccountsList.get(i).getClientID() + " | "
                    + updatedAccountsList.get(i).getBalance());
        }

        //Getting one of the accounts
        Account testGetAccount = accountJdbcDAO.getAccountByPK(20);
        System.out.println("Проверка работы метода получения одного счета:");
        System.out.println(testGetAccount.getid() + " | "
                + testGetAccount.getAccTypeID() + " | "
                + testGetAccount.getCurrencyID() + " | "
                + testGetAccount.getClientID() + " | "
                + testGetAccount.getBalance());*/
    }
}