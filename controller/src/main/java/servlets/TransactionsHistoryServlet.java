package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import beans.Transaction;
import daos.*;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        List accounts = new ArrayList();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            accounts = dao.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allAccounts", accounts);

        List clients = new ArrayList();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Client.class);
            clients = dao.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allClients", clients);

        List currencies = new ArrayList();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Currency.class);
            currencies = dao.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allCurrencies", currencies);

        request.getRequestDispatcher("transactionshistory.jsp").forward(request, response);
    }
}