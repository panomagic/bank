package servlets;

import beans.Transaction;
import beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.AccountService;
import services.ClientService;
import services.CurrencyService;
import services.TransactionService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="transactionshistory", urlPatterns={"/transactionshistory"})
@Controller
public class TransactionsHistoryServlet extends HttpServlet {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    TransactionService transactionService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Transaction> transactions = transactionService.getAllTransactions();

        request.setAttribute("allTransactions", transactions);

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        request.setAttribute("userrole", loggedUser.getRole());
        request.setAttribute("allAccounts", accountService.getAllAccounts());
        request.setAttribute("allClients", clientService.getAllClients());
        request.setAttribute("allCurrencies", currencyService.getAllCurrencies());

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}