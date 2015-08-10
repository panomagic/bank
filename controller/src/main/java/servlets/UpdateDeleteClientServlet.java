package servlets;

import beans.Client;
import beans.Gender;
import daos.GenericDAO;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateDeleteClientServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteClientServlet.class);

    Client client;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        try {
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Client.class);

            if (request.getParameter("action").equals("update")) {
                forwardPage = "updateclient.jsp";
                client = (Client) dao.getByPK(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("client", client);
            } else if (request.getParameter("action").equals("delete")) {
                forwardPage = "deleteclient.jsp";
                client = new Client();
                client.setid(Integer.parseInt(request.getParameter("id")));
                dao.delete(client);
                logger.info("Client with id " + client.getid() + " was deleted");
            }
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
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
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = factory.getContext();
            GenericDAO dao = factory.getDAO(connection, Client.class);
            dao.update(client);
            logger.info("Client with id " + client.getid() + " was updated");
        } catch (PersistException e) {
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
