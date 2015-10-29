package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/language")
public class LanguageServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("language", request.getParameter("chosenlanguage"));

        if (request.isUserInRole("ADMINISTRATOR"))
            response.sendRedirect("/admin");
        else if (request.isUserInRole("CLIENT"))
            response.sendRedirect("/clientinfo");
    }
}