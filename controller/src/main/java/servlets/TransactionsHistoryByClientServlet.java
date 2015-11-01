package servlets;

import beans.Transaction;
import beans.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import services.TransactionServiceImpl;
import services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static servlets.ClientInfoServlet.getAccountsClientsCurrencies;

@WebServlet(name="transactionshistorybyclient", urlPatterns={"/transactionshistorybyclient"})
public class TransactionsHistoryByClientServlet extends HttpServlet {
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        List<Transaction> transactions = new ArrayList<>();
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        for (int i = 0; i < allTransactions.size(); i++) {
            if ((allTransactions.get(i).getPayerID() == loggedUser.getClientID()) ||
                    (allTransactions.get(i).getRecipientID() == loggedUser.getClientID()))
                transactions.add(allTransactions.get(i));
        }

        request.setAttribute("allTransactions", transactions);

        request.setAttribute("userrole", loggedUser.getRole());

        getAccountsClientsCurrencies(request);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}