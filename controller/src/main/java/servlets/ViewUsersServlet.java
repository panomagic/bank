package servlets;

import beans.Client;
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

@WebServlet(name="viewusers", urlPatterns={"/viewusers"})
public class ViewUsersServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewClientsServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List users = new ArrayList();
        List clients = new ArrayList();
        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, User.class);
            users = dao.getAll();
            clients = fillClientsList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allUsers", users);
        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("viewusers.jsp").forward(request, response);
    }
}
