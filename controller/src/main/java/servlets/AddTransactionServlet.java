package servlets;

import beans.*;
import daos.*;
import mysql.MySQLDAOFactory;
import mysql.MySQLTransactionDAOImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static servlets.ClientInfoServlet.*;


@WebServlet(name="addtransaction", urlPatterns={"/addtransaction"})

public class AddTransactionServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddTransactionServlet.class);



    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Account> payerAccounts = new ArrayList<>();   //showing payer accounts separately for user roles: all accounts for admin and only his own for client
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        if (Role.ADMINISTRATOR == loggedUser.getRole()) {
            try {
                payerAccounts = fillAccountsList();
            } catch (PersistException e) {
                logger.error("MySQL DB error", e);
            }
        }
        else if (Role.CLIENT == loggedUser.getRole()) {
            try {
                List<Account> allAccounts = fillAccountsList();
                payerAccounts = fillUserAccountsList(loggedUser, payerAccounts, allAccounts);
            } catch (PersistException e) {
                logger.error("MySQL DB error", e);
            }
        }
        request.setAttribute("payerAccounts", payerAccounts);

        List<Account> recipientAccounts = null; //all recipient accounts list
        try {
            recipientAccounts = fillAccountsList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("recipientAccounts", recipientAccounts);

        List<Client> clients = null;
        List<Currency> currencies = null;

        try {
            clients = fillClientsList();
            currencies = fillCurrenciesList();

        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        request.setAttribute("allClients", clients);

        request.setAttribute("allCurrencies", currencies);

        request.setAttribute("userrole", loggedUser.getRole());

        request.getRequestDispatcher("addtransaction.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Transaction transaction = new Transaction();

        MySQLDAOFactory factory = new MySQLDAOFactory();
        GenericDAO daoAccount = null;

        Account payerAccount = null;
        try {
            Connection connection = factory.getContext();
            daoAccount = factory.getDAO(connection, Account.class);
            payerAccount = (Account) daoAccount.getByPK(Integer.parseInt(request.getParameter("choosepayeraccount")));
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        Account recipientAccount = null;
        try {
            Connection connection = factory.getContext();
            daoAccount = factory.getDAO(connection, Account.class);
            recipientAccount = (Account) daoAccount.getByPK(Integer.parseInt(request.getParameter("chooserecipientaccount")));
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        //checking currency matching in payer's and recipient's accounts
        if(payerAccount.getCurrencyID() != recipientAccount.getCurrencyID())
        {
            response.sendRedirect("transcurrencymismatch.jsp");
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with с id "
                    + recipientAccount.getid() + " was REJECTED due to currency mismatch");
            return;
        }

        transaction.setCurrencyID(payerAccount.getCurrencyID());
        transaction.setPayerID(payerAccount.getClientID());
        transaction.setPayerAccID(payerAccount.getid());

        transaction.setRecipientID(recipientAccount.getClientID());
        transaction.setRecipientAccID(recipientAccount.getid());
        transaction.setTransTypeID(3);
        transaction.setSum(new BigDecimal(Double.parseDouble(request.getParameter("sum"))));

        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("Error while creating connection", e);
        }
        MySQLTransactionDAOImpl mySQLTransactionDAO = new MySQLTransactionDAOImpl(connection);

        //checking for debit payer's account: transfer amount must be less or equal to balance
        if (payerAccount.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAccount.getBalance()) == 1) {
            response.sendRedirect("transoverdraft.jsp");
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with с id "
                     + recipientAccount.getid() + " was REJECTED: not enough money on payer's account");
            return;
        }

        try {
            mySQLTransactionDAO.addTransaction(transaction);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        if (loggedUser != null && Role.ADMINISTRATOR == loggedUser.getRole())
            response.sendRedirect("viewaccounts");
        else if (loggedUser != null && Role.CLIENT == loggedUser.getRole())
            response.sendRedirect("clientinfo");
    }
}