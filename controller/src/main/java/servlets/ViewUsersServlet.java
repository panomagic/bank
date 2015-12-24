package servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ClientService;
import services.UserService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="viewusers", urlPatterns={"/viewusers"})
@Controller
public class ViewUsersServlet extends HttpServlet {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("allUsers", userService.getAllUsers());
        request.setAttribute("allClients", clientService.getAllClients());

        request.getRequestDispatcher("viewusers.jsp").forward(request, response);
    }
}