package servlets;

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
        UserServiceImpl userService = new UserServiceImpl();
        ClientServiceImpl clientService = new ClientServiceImpl();

        request.setAttribute("allUsers", userService.getAllUsers());
        request.setAttribute("allClients", clientService.getAllClients());

        request.getRequestDispatcher("viewusers.jsp").forward(request, response);
    }
}