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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddClient extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        /*List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allClients", clients);*/
        request.getRequestDispatcher("addclient.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {

        Client client = new Client();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
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

        //request.setCharacterEncoding("UTF-8");

        //вызываем страницу с подтверждением успешного добавления клиента
        request.getRequestDispatcher("addclientresult.jsp").forward(request, response);

    }
}
