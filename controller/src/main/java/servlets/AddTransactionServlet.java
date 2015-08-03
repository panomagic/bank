package servlets;

import daos.AccountDAO;
import daos.ClientDAO;
import daos.CurrencyDAO;
import daos.TransactionDAO;
import beans.Account;
import beans.Role;
import beans.Transaction;
import beans.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="addtransaction", urlPatterns={"/addtransaction"})

public class AddTransactionServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddTransactionServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List payerAccounts = new ArrayList();   //showing payer accounts separately for user roles: all accounts for admin and only his own for client
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        if (Role.ADMINISTRATOR == loggedUser.getRole()) {
            try {
                payerAccounts = new AccountDAO().getAllAccounts();
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        }
        else if (Role.CLIENT == loggedUser.getRole()) {
            try {
                payerAccounts = new AccountDAO().getAccountsByClientID(loggedUser.getClientID());
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        }
        request.setAttribute("payerAccounts", payerAccounts);

        List recipientAccounts = new ArrayList(); //all recipient accounts list
            try {
                recipientAccounts = new AccountDAO().getAllAccounts();
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        request.setAttribute("recipientAccounts", recipientAccounts);

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

        request.setAttribute("userrole", loggedUser.getRole());

        request.getRequestDispatcher("addtransaction.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Transaction transaction = new Transaction();
        Account payerAccount = null;
        try {
            payerAccount = new AccountDAO().
                    getAccountByID(Integer.parseInt(request.getParameter("choosepayeraccount")));
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        Account recipientAccount = null;
        try {
            recipientAccount = new AccountDAO().
                    getAccountByID(Integer.parseInt(request.getParameter("chooserecipientaccount")));
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        //checking currency matching in payer's and recipient's accounts
        if(payerAccount.getCurrencyID() != recipientAccount.getCurrencyID())
        {
            response.sendRedirect("transcurrencymismatch.jsp");
            return;
        }

        transaction.setCurrencyID(payerAccount.getCurrencyID());
        transaction.setPayerID(payerAccount.getClientID());
        transaction.setPayerAccID(payerAccount.getAccountID());

        transaction.setRecipientID(recipientAccount.getClientID());
        transaction.setRecipientAccID(recipientAccount.getAccountID());
        transaction.setTransTypeID(3);
        transaction.setSum(new BigDecimal(Double.parseDouble(request.getParameter("sum"))));
        TransactionDAO transactionDAO = new TransactionDAO();

        //checking for debit payer's account: transfer amount must be less or equal to balance
        if (payerAccount.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAccount.getBalance()) == 1) {
            response.sendRedirect("transoverdraft.jsp");
            return;
        }

        try {
            transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        if (loggedUser != null && Role.ADMINISTRATOR == loggedUser.getRole())
            response.sendRedirect("viewaccounts");
        else if (loggedUser != null && Role.CLIENT == loggedUser.getRole())
            response.sendRedirect("viewaccountbyid");
    }
}
