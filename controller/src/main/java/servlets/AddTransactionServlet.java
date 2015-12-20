package servlets;

import beans.Account;
import beans.Role;
import beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
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

@Controller
public class AddTransactionServlet extends HttpServlet {

    @Autowired
    TransactionServiceImpl transactionService;

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

    public void forwardTo(HttpServletRequest request, HttpServletResponse response, int forwardType) throws ServletException, IOException {
        if (forwardType == 0)
            request.getRequestDispatcher("addtransactionresult.jsp").forward(request, response);

        if (forwardType == 1)
            request.getRequestDispatcher("transcurrencymismatch.jsp").forward(request, response);

        if (forwardType == 2)
            request.getRequestDispatcher("transtosameacc.jsp").forward(request, response);

        if (forwardType == 3)
            request.getRequestDispatcher("transoverdraft.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int payerAccID = Integer.parseInt(request.getParameter("choosepayeraccount"));
        int recipientAccID = Integer.parseInt(request.getParameter("chooserecipientaccount"));
        BigDecimal sum = new BigDecimal(Double.parseDouble(request.getParameter("sum")));

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-service-module.xml");
        //TransactionServiceImpl transactionService = (TransactionServiceImpl) appContext.getBean("transactionServiceImpl");

        int forwardType = transactionService.addTransactionService(payerAccID, recipientAccID, sum);

        forwardTo(request, response, forwardType);
    }
}