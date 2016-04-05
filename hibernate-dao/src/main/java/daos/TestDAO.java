package daos;

import entity.Account;
import entity.Client;
import entity.Gender;
import impl.AccountDAOImpl;
import impl.ClientDAOImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestDAO {
    public static void main(String[] args) throws ParseException {
        /*AccountDAO accountDAO = new AccountDAOImpl();

        Account account = new Account();

        account.setClientID(3);
        account.setAccTypeID(1);
        account.setCurrencyID(2);
        account.setBalance(new BigDecimal(7));
        //accountDAO.addAccount(account);
        List<Account> accounts = accountDAO.getAllAccounts();

        for (Account account1 : accounts) {
            System.out.println(account1.getid());
        }
*/

        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = new Client();
        /*client.setFullName("Treuq Poiugh");
        client.setGender("m");
        client.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse("30.05.1972"));
        client.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse("23.08.1992"));

        clientDAO.addClient(client);*/


        client.setid(40);
        clientDAO.deleteClient(client);
    }
}