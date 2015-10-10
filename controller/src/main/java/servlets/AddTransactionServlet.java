package servlets;

import beans.Account;
import beans.Role;
import beans.Transaction;
import beans.User;
import org.apache.log4j.Logger;
import services.AccountServiceImpl;
import services.TransactionServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
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
                payerAccounts = fillAccountsList();
        }
        else if (Role.CLIENT == loggedUser.getRole()) {
                List<Account> allAccounts = fillAccountsList();
                payerAccounts = fillUserAccountsList(loggedUser, payerAccounts, allAccounts);
        }
        request.setAttribute("payerAccounts", payerAccounts);

        request.setAttribute("recipientAccounts", fillAccountsList());

        request.setAttribute("allClients", fillClientsList());

        request.setAttribute("allCurrencies", fillCurrenciesList());

        request.setAttribute("userrole", loggedUser.getRole());

        request.getRequestDispatcher("addtransaction.jsp").forward(request, response);
    }

    private static Account getPayerAccount(HttpServletRequest request) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        return accountService.getAccountByID(Integer.parseInt(request.getParameter("choosepayeraccount")));
    }

    private static Account getRecipientAccount(HttpServletRequest request) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        return accountService.getAccountByID(Integer.parseInt(request.getParameter("chooserecipientaccount")));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Transaction transaction = new Transaction();

        Account payerAccount = getPayerAccount(request);
        Account recipientAccount = getRecipientAccount(request);

        if (payerAccount.getCurrencyID() != recipientAccount.getCurrencyID()) {
            request.getRequestDispatcher("transcurrencymismatch.jsp").forward(request, response);
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with id "
                    + recipientAccount.getid() + " was REJECTED due to currency mismatch");
            return;
        }

        if (payerAccount.getid() == recipientAccount.getid()) {
            request.getRequestDispatcher("transtosameacc.jsp").forward(request, response);
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to the same account " +
                    "was REJECTED");
            return;
        }

        transaction.setCurrencyID(payerAccount.getCurrencyID());
        transaction.setPayerID(payerAccount.getClientID());
        transaction.setPayerAccID(payerAccount.getid());

        transaction.setRecipientID(recipientAccount.getClientID());
        transaction.setRecipientAccID(recipientAccount.getid());
        transaction.setTransTypeID(3);
        transaction.setSum(new BigDecimal(Double.parseDouble(request.getParameter("sum"))));

        if (payerAccount.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAccount.getBalance()) == 1) {
            request.getRequestDispatcher("transoverdraft.jsp").forward(request, response);
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with id "
                     + recipientAccount.getid() + " was REJECTED: not enough money on payer's account");
            return;
        }

        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.addTransactionService(transaction);

        request.getRequestDispatcher("addtransactionresult.jsp").forward(request, response);
    }
}