package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import beans.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/clientinfo")
@Controller
public class ClientInfoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientInfoServlet.class);

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(userDetails.getUsername()))
                loggedUser = userList.get(i);
        }

        List<Account> accounts = new ArrayList<>();        //only accounts of user's client
        List<Account> allAccounts = accountService.getAllAccounts();         //all accounts list
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getClientID() == loggedUser.getClientID())
                accounts.add(allAccounts.get(i));
        }

        List<Client> clients = clientService.getAllClients();
        List<Currency> currencies = currencyService.getAllCurrencies();

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