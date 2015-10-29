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
import java.util.List;

import static servlets.ClientInfoServlet.getAccountsClientsCurrencies;

@WebServlet(name="transactionshistory", urlPatterns={"/transactionshistory"})
public class TransactionsHistoryServlet extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        List<Transaction> transactions = transactionService.getAllTransactions();

        request.setAttribute("allTransactions", transactions);

        //User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        User loggedUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(userDetails.getUsername()))
                loggedUser = userList.get(i);
        }
        request.setAttribute("userrole", loggedUser.getRole());

        getAccountsClientsCurrencies(request);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}