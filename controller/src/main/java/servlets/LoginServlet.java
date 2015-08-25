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

@WebServlet(name="login", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        User user = null;
        try {               //retrieving DB user with the same name as entered in the form
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, User.class);
            List<User> userList = dao.getAll();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserName().equals(request.getParameter("userName")))
                    user = userList.get(i);
            }
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        if (user != null) { //authentication procedure by password matching
            if(user.getPassword() != null && user.getPassword().equals(request.getParameter("password"))) {
                user.setPassword("");   //clearing the password in order to don't save it in the session
                request.getSession().setAttribute("LOGGED_USER", user);    //saving user-bean in the session
                if(user.getRole() == Role.ADMINISTRATOR) {
                    response.sendRedirect("/admin");
                    logger.info("Login as ADMINISTRATOR was performed by user " + user.getUserName());
                }
                else if(user.getRole() == Role.CLIENT) {
                    response.sendRedirect("/clientinfo");
                    logger.info("Login as CLIENT was performed by user " + user.getUserName());
                }
            }
            else {
                response.sendRedirect("loginfailed.jsp");
                request.getSession().setAttribute("language", request.getParameter("chosenlanguage"));    //saving interface language in session
                logger.info("Login was not performed for user " + request.getParameter("userName"));
            }
        }

        request.getSession().setAttribute("language", request.getParameter("chosenlanguage"));    //saving interface language in session
    }
}