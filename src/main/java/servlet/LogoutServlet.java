package servlet;

import bean.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="logout", urlPatterns={"/logout"})
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class);
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("LOGGED_USER");
        session.invalidate();
        request.getRequestDispatcher("logout.jsp").forward(request, response);
        logger.info("Logout was performed for user " + loggedUser.getUserName() +
                " as " + loggedUser.getRole());
    }
}
