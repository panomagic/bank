package servlet;

import bean.Client;
import bean.Gender;
import dao.ClientDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class UpdateDeleteClient extends HttpServlet {
        Client client;

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        if(request.getParameter("action").equals("update")) {
            forwardPage = "updateclient.jsp";
            try {
                client = new ClientDAO().getClientByID(Integer.parseInt(request.getParameter("clientID")));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("client", client);

            try {
                new ClientDAO().updateClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(request.getParameter("action").equals("delete")) {
            forwardPage = "deleteclient.jsp";
            client = new Client();
            client.setClientID(Integer.parseInt(request.getParameter("clientID")));
            try {
                new ClientDAO().deleteClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Client client = new Client();
        client.setFullName(request.getParameter("fullname"));
        client.setGender(Gender.fromString(request.getParameter("gender")));
        client.setDateOfBirth(new Date(request.getParameter("dateofbirth")));  //УЛУЧШИТЬ РАБОТУ С ДАТАМИ!
        client.setDateOfReg(new Date(request.getParameter("dateofreg")));    //УЛУЧШИТЬ РАБОТУ С ДАТАМИ!!!

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

        try {
            new ClientDAO().updateClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //возвращаемся на страницу со списком клиентов
        request.getRequestDispatcher("viewclients").forward(request, response);

    }
}
