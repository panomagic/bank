package servlets;

import beans.Account;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.AccountService;
import services.ClientService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="updatedeleteaccount", urlPatterns={"/updatedeleteaccount"})
@Controller
public class UpdateDeleteAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteAccountServlet.class);

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    Account account;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        request.setAttribute("allClients", clientService.getAllClients());

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