package servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.AccountService;
import services.ClientService;
import services.CurrencyService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ViewAccountsServlet extends HttpServlet {
    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    CurrencyService currencyService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("allAccounts", accountService.getAllAccounts());
        request.setAttribute("allClients", clientService.getAllClients());
        request.setAttribute("allCurrencies", currencyService.getAllCurrencies());

        request.getRequestDispatcher("viewaccounts.jsp").forward(request, response);
    }
}