package servlets;

import beans.Transaction;
import beans.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.TransactionServiceImpl;
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
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");
        TransactionServiceImpl transactionService = (TransactionServiceImpl) appContext.getBean("transactionServiceImpl");

        List<Transaction> transactions = transactionService.getAllTransactions();

        request.setAttribute("allTransactions", transactions);

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        request.setAttribute("userrole", loggedUser.getRole());

        getAccountsClientsCurrencies(request);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}