package servlets;

import beans.Role;
import beans.User;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static servlets.ClientInfoServlet.fillClientsList;

@WebServlet(name="updatedeleteuser", urlPatterns={"/updatedeleteuser"})
public class UpdateDeleteUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteUserServlet.class);

    User user;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        List clients = new ArrayList();
        try {
            clients = fillClientsList();
            clients.add(0, null);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, User.class);

            if ("update".equals(request.getParameter("action"))) {
                forwardPage = "updateuser.jsp";
                user = (User) dao.getByPK(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("user", user);
            } else if ("delete".equals(request.getParameter("action"))) {
                forwardPage = "deleteuser.jsp";
                user = new User();
                user.setid(Integer.parseInt(request.getParameter("id")));
                dao.delete(user);
                logger.info("User with id " + user.getid() + " was deleted");
            }
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
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
        user.setRole(Role.fromString(request.getParameter("role")));


        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, User.class);
            dao.update(user);
            logger.info("User with id " + user.getid() + " was updated");
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateUser(request);

        response.sendRedirect("admin");  //return to main page
        return;
    }
}