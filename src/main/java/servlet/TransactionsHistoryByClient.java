package servlet;

import bean.User;
import dao.AccountDAO;
import dao.ClientDAO;
import dao.CurrencyDAO;
import dao.TransactionDAO;

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
public class TransactionsHistoryByClient extends HttpServlet {
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        List transactions = new ArrayList();
        try {
            transactions = new TransactionDAO().getTransactionsByClientID(loggedUser.getClientID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("allTransactions", transactions);

        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("allAccounts", accounts);

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("allClients", clients);

        List currencies = new ArrayList();
        try {
            currencies = new CurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("allCurrencies", currencies);

        //request.setAttribute("userRole", loggedUser.getRole());

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}