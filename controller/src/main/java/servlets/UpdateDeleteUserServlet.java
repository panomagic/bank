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

@WebServlet(name="updatedeleteuser", urlPatterns={"/updatedeleteuser"})
public class UpdateDeleteUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteUserServlet.class);

    User user;
    UserServiceImpl userService = new UserServiceImpl();

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        request.setAttribute("allClients", fillClientsList());

        if ("update".equals(request.getParameter("action"))) {
            forwardPage = "updateuser.jsp";
            user = userService.getUserByID(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("user", user);
        } else if ("delete".equals(request.getParameter("action"))) {
            forwardPage = "deleteuserresult.jsp";
            user = new User();
            user.setid(Integer.parseInt(request.getParameter("id")));
            userService.deleteUser(user);
            logger.info("User with id " + user.getid() + " was deleted");
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateUser(HttpServletRequest request) {
        user.setUserName(request.getParameter("username"));
        user.setPsw(request.getParameter("psw"));
        System.out.println("нач" + request.getParameter("chooseclient") + "кон");
        System.out.println(request.getParameter("chooseclient").equals(""));
        if (request.getParameter("chooseclient").equals("")) {
            Integer a = null;
            user.setClientID(a);
        } else
            user.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        user.setRole(Role.CLIENT);

        userService.updateUser(user);
        logger.info("User with id " + user.getid() + " was updated");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateUser(request);

        request.getRequestDispatcher("updateuserresult.jsp").forward(request, response);
        return;
    }
}