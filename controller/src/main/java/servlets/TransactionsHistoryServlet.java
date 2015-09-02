package servlets;

import beans.Transaction;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static servlets.ClientInfoServlet.getAccountsClientsCurrencies;

@WebServlet(name="transactionshistory", urlPatterns={"/transactionshistory"})
public class TransactionsHistoryServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TransactionsHistoryServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List transactions = new ArrayList();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Transaction.class);
            transactions = dao.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allTransactions", transactions);

        getAccountsClientsCurrencies(request);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}