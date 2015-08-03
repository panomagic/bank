package servlets;

import daos.AccountDAO;
import daos.ClientDAO;
import beans.Account;
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

@WebServlet(name="updatedeleteaccount", urlPatterns={"/updatedeleteaccount"})
public class UpdateDeleteAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteAccountServlet.class);

    Account account;
    AccountDAO accountDAO = new AccountDAO();
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        if(request.getParameter("action").equals("update")) {
            forwardPage = "updateaccount.jsp";
            try {
                account = accountDAO.getAccountByID(Integer.parseInt(request.getParameter("accountID")));
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
            request.setAttribute("account", account);

            try {
                accountDAO.updateAccount(account);
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        } else if(request.getParameter("action").equals("delete")) {
            forwardPage = "deleteaccount.jsp";
            account = new Account();
            account.setAccountID(Integer.parseInt(request.getParameter("accountID")));
            try {
                new AccountDAO().deleteAccount(account);
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateAccount(HttpServletRequest request) {
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

        try {
            accountDAO.updateAccount(account);
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateAccount(request);

        //return to accounts list page
        response.sendRedirect("viewaccounts");
        return;
    }
}
