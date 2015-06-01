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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        try {
            client.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofbirth")));
            client.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofreg")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            new ClientDAO().addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //вызываем страницу с подтверждением успешного добавления клиента
        request.getRequestDispatcher("addclientresult.jsp").forward(request, response);
    }
}