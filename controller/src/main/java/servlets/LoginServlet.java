package servlets;

import daos.UserDAO;
import beans.Role;
import beans.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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
        try {               //retriving DB user with the same name as entered in the form
            user = new UserDAO().getUserByUserName(request.getParameter("userName"));
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        if (user != null) { //authentication procedure by password mathching
            if(user.getPassword() != null && user.getPassword().equals(request.getParameter("password"))) {
                user.setPassword("");   //clearing the password in order to don't save it in the session
                request.getSession().setAttribute("LOGGED_USER", user);    //saving user-bean in the session
                if(user.getRole() == Role.ADMINISTRATOR) {
                    response.sendRedirect("admin.jsp");
                    logger.info("Login as ADMINISTRATOR was performed");
                }
                else if(user.getRole() == Role.CLIENT) {
                    response.sendRedirect("/viewaccountbyid");
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
