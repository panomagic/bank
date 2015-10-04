package servlets;

import beans.Role;
import beans.User;
import org.apache.log4j.Logger;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="adduser", urlPatterns={"/adduser"})
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("allClients", fillClientsList());

        request.getRequestDispatcher("adduser.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        User user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setPsw(request.getParameter("psw"));
        user.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        user.setRole(Role.fromString(request.getParameter("role")));

        UserServiceImpl userService = new UserServiceImpl();
        userService.addUser(user);
        logger.info("New user was added for client with id " + user.getClientID());

        request.getRequestDispatcher("adduserresult.jsp").forward(request, response);
    }
}