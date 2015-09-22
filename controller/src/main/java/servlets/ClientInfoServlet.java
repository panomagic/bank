package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import beans.User;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;
import services.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/clientinfo")
public class ClientInfoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientInfoServlet.class);

    static List<Account> fillAccountsList() throws PersistException {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Account.class);
        AccountServiceImpl accountService = new AccountServiceImpl();
        return accountService.getAllAccounts(dao);
    }

    static List<Account> fillUserAccountsList(User loggedUser, List<Account> accounts, List<Account> allAccounts) throws PersistException {
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getClientID() == loggedUser.getClientID())
                accounts.add(allAccounts.get(i));
        }
        return accounts;
    }

    static List<Client> fillClientsList() throws PersistException {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Client.class);
        return dao.getAll();
    }

    static List<Currency> fillCurrenciesList() throws PersistException {
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Currency.class);
        return dao.getAll();
    }

    static void getAccountsClientsCurrencies(HttpServletRequest request) {
        List accounts = new ArrayList();
        List clients = new ArrayList();
        List currencies = new ArrayList();
        try {
            accounts = fillAccountsList();
            clients = fillClientsList();
            currencies = fillCurrenciesList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allAccounts", accounts);
        request.setAttribute("allClients", clients);
        request.setAttribute("allCurrencies", currencies);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        List<Account> accounts = new ArrayList<>();        //only accounts of user's client
        List<Account> allAccounts;           //all accounts list


        List<Client> clients = null;
        List<Currency> currencies = null;

        try {
            allAccounts = fillAccountsList();
            accounts = fillUserAccountsList(loggedUser, accounts, allAccounts);
            clients = fillClientsList();
            currencies = fillCurrenciesList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        Object[][] records = new Object[accounts.size()][];
        String clientName = null;
        String accType = null;
        String currency = null;
        for (int i = 0; i < accounts.size(); i++) {
            for (int j = 0; j < clients.size(); j++) {
                if (accounts.get(i).getClientID() == clients.get(j).getid())
                    clientName = clients.get(j).getFullName();
            }

            if (accounts.get(i).getAccTypeID() == 1)
                accType = "DEBIT";
            else if (accounts.get(i).getAccTypeID() == 2)
                accType = "CREDIT";

            for (int j = 0; j < currencies.size(); j++) {
                if (accounts.get(i).getCurrencyID() == currencies.get(j).getid())
                    currency = currencies.get(j).getCurrencyName();
            }
            records[i] = new Object[]{
                    accounts.get(i).getid(),
                    clientName,
                    accType,
                    currency,
                    accounts.get(i).getBalance()
            };
        }
        request.setAttribute("records", records);
        request.getRequestDispatcher("client.jsp").forward(request, response);
    }
}