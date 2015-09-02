package servlets;

import daos.PersistException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static servlets.ClientInfoServlet.fillClientsList;

public class ViewClientsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewClientsServlet.class);

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List clients = new ArrayList();
        try {
            clients = fillClientsList();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("viewclients.jsp").forward(request, response);
    }
}