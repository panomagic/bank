package servlet;

import bean.*;
import dao.ClientDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AddClient extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addclient.jsp").forward(request, response);
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
            new ClientDAO().addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Клиент " + client.getFullName() + " добавлен");

        //вызываем страницу с подтверждением успешного добавления клиента
        request.getRequestDispatcher("addclientresult.jsp").forward(request, response);

    }
}
