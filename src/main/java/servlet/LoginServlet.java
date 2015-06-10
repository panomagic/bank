package servlet;

import service.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="loginservlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Users user = new Users();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));

        if(user.isAdmin(user))
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        else if(user.isClient(user))
            request.getRequestDispatcher("client.jsp").forward(request, response);
        else
            request.getRequestDispatcher("authfailed.jsp").forward(request, response);

    }
}
