package servlet;

import bean.*;
import dao.*;

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

public class AddTransaction extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List payerAccounts = new ArrayList();   //счета плат-ков - в зав. от роли (свои для клиента и все для админа)
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        if (Role.ADMINISTRATOR == loggedUser.getRole()) {
            try {
                payerAccounts = new AccountDAO().getAllAccounts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (Role.CLIENT == loggedUser.getRole()) {
            try {
                payerAccounts = new AccountDAO().getAccountsByClientID(loggedUser.getClientID());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("payerAccounts", payerAccounts);

        List recipientAccounts = new ArrayList(); //счета получателей - полный список
            try {
                recipientAccounts = new AccountDAO().getAllAccounts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        request.setAttribute("recipientAccounts", recipientAccounts);

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
            e.printStackTrace();
        }

        Account recipientAccount = null;
        try {
            recipientAccount = new AccountDAO().
                    getAccountByID(Integer.parseInt(request.getParameter("chooserecipientaccount")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //проверка соответствия валюты счета получателя и отправителя
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

        //проверка для дебитного счета плательщика (сумма транзакции не больше баланса):
        if (payerAccount.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAccount.getBalance()) == 1) {
            response.sendRedirect("transoverdraft.jsp");
            return;
        }

        try {
            transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        if (loggedUser != null && Role.ADMINISTRATOR == loggedUser.getRole())
            response.sendRedirect("viewaccounts");
        else if (loggedUser != null && Role.CLIENT == loggedUser.getRole())
            response.sendRedirect("viewaccountbyid");
    }
}