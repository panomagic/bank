package servlets;

import beans.Account;
import beans.Role;
import beans.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="addtransaction", urlPatterns={"/addtransaction"})
@Controller
public class AddTransactionServlet extends HttpServlet {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Account> payerAccounts = new ArrayList<>();   //showing payer accounts separately for user roles: all accounts for admin and only his own for client
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        List<Account> clientAccountsList = new ArrayList<>();
        List<Account> allAccountsList = accountService.getAllAccounts();
            for (int i = 0; i < allAccountsList.size(); i++) {
                if (allAccountsList.get(i).getClientID() == loggedUser.getClientID())
                    clientAccountsList.add(allAccountsList.get(i));
            }

        if (Role.ADMINISTRATOR == loggedUser.getRole())
            payerAccounts = allAccountsList;
        else if (Role.CLIENT == loggedUser.getRole())
            payerAccounts = clientAccountsList;

        request.setAttribute("payerAccounts", payerAccounts);

        request.setAttribute("recipientAccounts", allAccountsList);

        request.setAttribute("allClients", clientService.getAllClients());

        request.setAttribute("allCurrencies", currencyService.getAllCurrencies());

        request.setAttribute("userrole", loggedUser.getRole());

        request.getRequestDispatcher("addtransaction.jsp").forward(request, response);
    }

    public void forwardTo(HttpServletRequest request, HttpServletResponse response, int forwardType) throws ServletException, IOException {
        if (forwardType == 0)
            request.getRequestDispatcher("addtransactionresult.jsp").forward(request, response);

        if (forwardType == 1)
            request.getRequestDispatcher("transcurrencymismatch.jsp").forward(request, response);

        if (forwardType == 2)
            request.getRequestDispatcher("transtosameacc.jsp").forward(request, response);

        if (forwardType == 3)
            request.getRequestDispatcher("transoverdraft.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int payerAccID = Integer.parseInt(request.getParameter("choosepayeraccount"));
        int recipientAccID = Integer.parseInt(request.getParameter("chooserecipientaccount"));
        BigDecimal sum = new BigDecimal(Double.parseDouble(request.getParameter("sum")));

        int forwardType = transactionService.addTransactionService(payerAccID, recipientAccID, sum);

        forwardTo(request, response, forwardType);
    }
}