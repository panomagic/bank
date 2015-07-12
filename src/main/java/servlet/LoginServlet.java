package servlet;

import bean.Role;
import bean.User;
import dao.UserDAO;
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
        try {               //получаем пользователя из БД с именем, как у введенного в форме
            user = new UserDAO().getUserByUserName(request.getParameter("userName"));
        } catch (SQLException e) {
            logger.warn("Ошибка БД MySQL", e); //e.printStackTrace();
        }

        if (user != null) { //процедура аутентификации по совпадению пароля
            if(user.getPassword() != null && user.getPassword().equals(request.getParameter("password"))) {
                user.setPassword("");   //стираем пароль, чтобы не хранить его в сессии
                request.getSession().setAttribute("LOGGED_USER", user);    //сохраняем user-бин в сессии
                if(user.getRole() == Role.ADMINISTRATOR) {
                    response.sendRedirect("admin.jsp");
                    logger.info("Выполнен вход в роли администратора");
                }
                else if(user.getRole() == Role.CLIENT) {
                    response.sendRedirect("/viewaccountbyid");
                    logger.info("Выполнен вход в роли клиента для пользователя " + user.getUserName());
                }
            }
            else {
                response.sendRedirect("loginfailed.jsp");
                logger.info("Вход не выполнен для пользователя " + request.getParameter("userName"));
            }
        }
    }
}