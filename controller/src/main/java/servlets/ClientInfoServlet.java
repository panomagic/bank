package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import beans.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import services.AccountServiceImpl;
import services.ClientServiceImpl;
import services.CurrencyServiceImpl;
import services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/clientinfo")
public class ClientInfoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientInfoServlet.class);

    static List<Account> fillAccountsList() {
        AccountServiceImpl accountService = new AccountServiceImpl();
        return accountService.getAllAccounts();
    }

    static List<Account> fillUserAccountsList(User loggedUser, List<Account> accounts, List<Account> allAccounts) {
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getClientID() == loggedUser.getClientID())
                accounts.add(allAccounts.get(i));
        }
        return accounts;
    }

    static List<Client> fillClientsList() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        return clientService.getAllClients();
    }

    static List<Currency> fillCurrenciesList() {
        CurrencyServiceImpl currencyService = new CurrencyServiceImpl();
        return currencyService.getAllCurrencies();
    }

    static void getAccountsClientsCurrencies(HttpServletRequest request) {
        request.setAttribute("allAccounts", fillAccountsList());
        request.setAttribute("allClients", fillClientsList());
        request.setAttribute("allCurrencies", fillCurrenciesList());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        User loggedUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(userDetails.getUsername()))
                loggedUser = userList.get(i);
        }

        List<Account> accounts = new ArrayList<>();        //only accounts of user's client
        List<Account> allAccounts;           //all accounts list

        allAccounts = fillAccountsList();
        accounts = fillUserAccountsList(loggedUser, accounts, allAccounts);
        List<Client> clients = fillClientsList();
        List<Currency> currencies = fillCurrenciesList();

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