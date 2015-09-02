package servlets;

import beans.Account;
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

import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="addaccount", urlPatterns={"/addaccount"})
public class AddAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddAccountServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List clients = new ArrayList();
        try {
            clients = fillClientsList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("addaccount.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Account account = new Account();
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            dao.persist(account);
            logger.info("New account was added for client with id " + account.getClientID());
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.getRequestDispatcher("addaccountresult.jsp").forward(request, response);
    }
}