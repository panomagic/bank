package servlet;

import dao.ClientDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewClientsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewClientsServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("viewclients.jsp").forward(request, response);
    }
}