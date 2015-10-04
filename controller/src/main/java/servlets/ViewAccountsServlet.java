package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ClientInfoServlet.getAccountsClientsCurrencies;

public class ViewAccountsServlet extends HttpServlet {
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getAccountsClientsCurrencies(request);

        request.getRequestDispatcher("viewaccounts.jsp").forward(request, response);
    }
}