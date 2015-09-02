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

@WebServlet(name="updatedeleteaccount", urlPatterns={"/updatedeleteaccount"})
public class UpdateDeleteAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteAccountServlet.class);

    Account account;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        List clients = new ArrayList();
        try {
            clients = fillClientsList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);

            if ("update".equals(request.getParameter("action"))) {
                forwardPage = "updateaccount.jsp";
                account = (Account) dao.getByPK(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("account", account);
            } else if ("delete".equals(request.getParameter("action"))) {
                forwardPage = "deleteaccount.jsp";
                account = new Account();
                account.setid(Integer.parseInt(request.getParameter("id")));
                dao.delete(account);
                logger.info("Account with id " + account.getid() + " was deleted");
            }
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateAccount(HttpServletRequest request) {
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Account.class);
            dao.update(account);
            logger.info("Account with id " + account.getid() + " was updated");
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateAccount(request);

        response.sendRedirect("viewaccounts");  //return to accounts list page
        return;
    }
}