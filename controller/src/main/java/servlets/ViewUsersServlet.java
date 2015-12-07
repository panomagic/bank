package servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.ClientServiceImpl;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="viewusers", urlPatterns={"/viewusers"})
public class ViewUsersServlet extends HttpServlet {
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");
        UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userServiceImpl");

        ClientServiceImpl clientService = (ClientServiceImpl) appContext.getBean("clientServiceImpl");

        request.setAttribute("allUsers", userService.getAllUsers());
        request.setAttribute("allClients", clientService.getAllClients());

        request.getRequestDispatcher("viewusers.jsp").forward(request, response);
    }
}