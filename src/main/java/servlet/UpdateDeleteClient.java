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
        Client client = new Client();

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        if(request.getParameter("action").equals("update")) {
            forwardPage = "updateclient.jsp";
            client.setClientID(Integer.parseInt(request.getParameter("clientID")));



            try {
                new ClientDAO().updateClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(request.getParameter("action").equals("delete")) {
            forwardPage = "deleteclient.jsp";
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
        //возвращаемся на страницу со списком клиентов - ДОБАВИТЬ СООБЩЕНИЕ "Клиент удален"
        request.getRequestDispatcher("viewclients").forward(request, response);

    }
}
