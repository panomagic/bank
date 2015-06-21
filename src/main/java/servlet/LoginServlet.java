package servlet;

import bean.Role;
import bean.User;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="login", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {

        User user = null;
        try {               //получаем пользователя из БД с именем, как у введенного в форме
            user = new UserDAO().getUserByUserName(request.getParameter("userName"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) { //процедура аутентификации по совпадению пароля
            if(user.getPassword() != null && user.getPassword().equals(request.getParameter("password"))) {
                user.setPassword("");   //стираем пароль, чтобы не хранить его в сессии
                request.getSession().setAttribute("LOGGED_USER", user);    //сохраняем user-бин в сессии
                 if(user.getRole() == Role.ADMINISTRATOR) {
                     response.sendRedirect("admin.jsp");
                 }
                 else if(user.getRole() == Role.CLIENT) {
                     response.sendRedirect("/viewaccountbyid");
                 }
             }
             else
                response.sendRedirect("loginfailed.jsp");
        }
    }
}