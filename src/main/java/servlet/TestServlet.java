package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("name", "vp");

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);  // указываем диспетчеру jsp-страницу, к-я будет отображаться при обращении к данному методуGET
                                    //метод forward перенаправляет запрос на jsp-страницу
    }
}
