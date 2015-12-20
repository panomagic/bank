package servlets;

import beans.Role;
import beans.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import services.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="adduser", urlPatterns={"/adduser"})
@Controller
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);

    @Autowired
    UserServiceImpl userService;

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
        user.setRole(Role.CLIENT);

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-service-module.xml");
        //UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userServiceImpl");

        userService.addUser(user);
        logger.info("New user was added for client with id " + user.getClientID());

        request.getRequestDispatcher("adduserresult.jsp").forward(request, response);
    }
}