package servlet;

import dao.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
