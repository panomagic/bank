package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import beans.User;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

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
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        List<Account> accounts = new ArrayList<Account>();        //only accounts of user's client
        List<Account> allAccounts = new ArrayList<Account>();           //all accounts list
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            allAccounts = dao.getAll();
            for (int i = 0; i < allAccounts.size(); i++) {
                if (allAccounts.get(i).getClientID() == loggedUser.getClientID())
                    accounts.add(allAccounts.get(i));
            }
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        List<Client> clients = new ArrayList<Client>();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Client.class);
            clients = dao.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        List<Currency> currencies = new ArrayList<Currency>();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Currency.class);
            currencies = dao.getAll();
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