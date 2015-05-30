package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="testservlet", urlPatterns={"/testservlet"})
public class TestServlet extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       request.setAttribute("name", "vp2");

        request.getRequestDispatcher("/viewaccounts").forward(request, response);

    }
}
