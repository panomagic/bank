package servlet;

import bean.Client;
import dao.AccountDAO;
import dao.ClientDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="addtransaction", urlPatterns={"/addtransaction"})

public class AddTransaction extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        request.setAttribute("allClients", clients);
        request.getRequestDispatcher("addtransaction.jsp").forward(request, response);
    }
}
