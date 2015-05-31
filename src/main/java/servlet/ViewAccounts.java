package servlet;

import bean.*;
import dao.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //создаем инстанс драйвера jdbc для подключения Tomcat к MySQL
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allAccounts", accounts);

        request.getRequestDispatcher("viewaccounts.jsp").forward(request, response);
    }
}
