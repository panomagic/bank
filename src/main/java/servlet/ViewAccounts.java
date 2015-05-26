package servlet;

import bean.*;
import dao.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends HttpServlet {

    @Override
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

        /*
        Account account = null;
        try {
            account = new dao.AccountDAO().getAccountByID(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String accountToString = account.getClientID() + ", на счету " + account.getBalance();

        request.setAttribute("account", accountToString);

        request.getRequestDispatcher("viewclients.jsp").forward(request, response);
        */


        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allAccounts", accounts);

        request.getRequestDispatcher("viewaccounts.jsp").forward(request, response);
    }

    public static void main(String[] args) throws SQLException {
        //Account account = new AccountDAO().getAccountByID(10);
        Client client = new ClientDAO().getClientByAccountID(10);
        //Client client = new ClientDAO().getClientByID(10);
        System.out.println(client.getFullName());
    }
}
