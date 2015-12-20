package servlets;

import beans.Account;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import services.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="updatedeleteaccount", urlPatterns={"/updatedeleteaccount"})
@Controller
public class UpdateDeleteAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteAccountServlet.class);

    @Autowired
    AccountServiceImpl accountService;

    Account account;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        request.setAttribute("allClients", fillClientsList());

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-service-module.xml");
        //AccountServiceImpl accountService = (AccountServiceImpl) appContext.getBean("accountServiceImpl");

        if ("update".equals(request.getParameter("action"))) {
            forwardPage = "updateaccount.jsp";
            account = accountService.getAccountByID(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("account", account);
        } else if ("delete".equals(request.getParameter("action"))) {
            forwardPage = "deleteaccountresult.jsp";
            account = new Account();
            account.setid(Integer.parseInt(request.getParameter("id")));
            accountService.deleteAccount(account);
            logger.info("Account with id " + account.getid() + " was deleted");
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateAccount(HttpServletRequest request) {
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-service-module.xml");
        //AccountServiceImpl accountService = (AccountServiceImpl) appContext.getBean("accountServiceImpl");

        accountService.updateAccount(account);
        logger.info("Account with id " + account.getid() + " was updated");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateAccount(request);

        request.getRequestDispatcher("updateaccountresult.jsp").forward(request, response);
        return;
    }
}