package servlets;

import beans.User;
import beans.Role;
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

@WebServlet(name="adduser", urlPatterns={"/adduser"})
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List clients = new ArrayList();
        try {
            clients = fillClientsList();
            clients.add(0, null);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

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

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, User.class);
            dao.persist(user);
            logger.info("New user was added for client with id " + user.getClientID());
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.getRequestDispatcher("adduserresult.jsp").forward(request, response);
    }
}