package servlet;

import bean.Account;
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

@WebServlet(name="addaccount", urlPatterns={"/addaccount"})
public class AddAccount extends HttpServlet {
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

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("addaccount.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Account account = new Account();
        account.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        account.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
        account.setAccTypeID(Integer.parseInt(request.getParameter("acctypeID")));

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
            new AccountDAO().addAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //вызываем страницу с подтверждением успешного добавления клиента
        request.getRequestDispatcher("addaccountresult.jsp").forward(request, response);
    }
}