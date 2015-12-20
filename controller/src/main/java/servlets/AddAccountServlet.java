package servlets;

import beans.Account;
import mysql.MySQLAccountDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.AccountServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="addaccount", urlPatterns={"/addaccount"})
@Controller
public class AddAccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddAccountServlet.class);

    @Autowired
    AccountServiceImpl accountService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List clients = fillClientsList();

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("addaccount.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Account account = new Account();
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

        /*ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");

        AccountServiceImpl accountService = (AccountServiceImpl) appContext.getBean("accountServiceImpl");*/
        //logger.info("accountService = " + accountService + " and account = " + account);
        accountService.addAccount(account);
        logger.info("New account was added for client with id " + account.getClientID());

        request.getRequestDispatcher("addaccountresult.jsp").forward(request, response);
    }
}