package servlet;

import dao.AccountDAO;
import dao.ClientDAO;
import dao.CurrencyDAO;
import dao.TransactionDAO;
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

@WebServlet(name="transactionshistory", urlPatterns={"/transactionshistory"})
public class TransactionsHistory extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TransactionsHistory.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List transactions = new ArrayList();
        try {
            transactions = new TransactionDAO().getAllTransactions();
        } catch (SQLException e) {
            logger.warn("Ошибка БД MySQL", e); //e.printStackTrace();
        }
        request.setAttribute("allTransactions", transactions);

        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            logger.warn("Ошибка БД MySQL", e); //e.printStackTrace();
        }
        request.setAttribute("allAccounts", accounts);

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.warn("Ошибка БД MySQL", e); //e.printStackTrace();
        }
        request.setAttribute("allClients", clients);

        List currencies = new ArrayList();
        try {
            currencies = new CurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            logger.warn("Ошибка БД MySQL", e); //e.printStackTrace();
        }
        request.setAttribute("allCurrencies", currencies);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}