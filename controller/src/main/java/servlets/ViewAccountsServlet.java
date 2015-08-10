package servlets;

import beans.Account;
import beans.Client;
import beans.Currency;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ViewAccountsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewAccountsServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            //clients = new ClientDAO().getAllClients();
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

        request.getRequestDispatcher("viewaccounts.jsp").forward(request, response);
    }
}