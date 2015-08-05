package servlets;

import beans.Currency;
import daos.*;
import beans.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/clientinfo")
public class ClientInfoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientInfoServlet.class);
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        List<Account> accounts = new ArrayList<Account>();
        try {
            accounts = new AccountDAO().getAccountsByClientID(loggedUser.getClientID());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        List<Client> clients = new ArrayList<Client>();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        List<Currency> currencies = new ArrayList<Currency>();
        try {
            currencies = new CurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        Object[][] records = new Object[accounts.size()][];
        String clientName = null;
        String accType = null;
        String currency = null;
        for (int i = 0; i < accounts.size(); i++) {
            for (int j = 0; j < clients.size(); j++) {
                if (accounts.get(i).getClientID() == clients.get(j).getClientID())
                    clientName = clients.get(j).getFullName();
            }

            if (accounts.get(i).getAccTypeID() == 1)
                accType = "DEBIT";
            else if (accounts.get(i).getAccTypeID() == 2)
                accType = "CREDIT";

            for (int j = 0; j < currencies.size(); j++) {
                if (accounts.get(i).getCurrencyID() == currencies.get(j).getCurrencyID())
                    currency = currencies.get(j).getCurrency();
            }
            records[i] = new Object[]{
                    accounts.get(i).getAccountID(),
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