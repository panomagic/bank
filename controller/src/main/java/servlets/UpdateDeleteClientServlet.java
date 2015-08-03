package servlets;

import daos.ClientDAO;
import beans.Client;
import beans.Gender;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateDeleteClientServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteClientServlet.class);

    Client client;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        if(request.getParameter("action").equals("update")) {
            forwardPage = "updateclient.jsp";
            try {
                client = new ClientDAO().getClientByID(Integer.parseInt(request.getParameter("clientID")));
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }

            request.setAttribute("client", client);

            try {
                new ClientDAO().updateClient(client);
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        } else if(request.getParameter("action").equals("delete")) {
            forwardPage = "deleteclient.jsp";
            client = new Client();
            client.setClientID(Integer.parseInt(request.getParameter("clientID")));
            try {
                new ClientDAO().deleteClient(client);
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateClient(HttpServletRequest request) {
        client.setFullName(request.getParameter("fullname"));
        client.setGender(Gender.fromString(request.getParameter("gender")));
        try {
            client.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofbirth")));
            client.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofreg")));
        } catch (ParseException e) {
            logger.warn("Date parsing error", e);
        }
        try {
            new ClientDAO().updateClient(client);
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateClient(request);

        //return to clients list page
        response.sendRedirect("viewclients");
        return;
    }
}
