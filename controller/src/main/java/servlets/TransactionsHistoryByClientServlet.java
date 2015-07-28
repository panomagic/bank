package servlets;

import daos.AccountDAO;
import daos.ClientDAO;
import daos.CurrencyDAO;
import daos.TransactionDAO;
import beans.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="transactionshistorybyclient", urlPatterns={"/transactionshistorybyclient"})
public class TransactionsHistoryByClientServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TransactionsHistoryByClientServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        List transactions = new ArrayList();
        try {
            transactions = new TransactionDAO().getTransactionsByClientID(loggedUser.getClientID());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allTransactions", transactions);

        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allAccounts", accounts);

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allClients", clients);

        List currencies = new ArrayList();
        try {
            currencies = new CurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allCurrencies", currencies);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}