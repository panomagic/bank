package servlet;


import bean.*;
import dao.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewClients extends HttpServlet {

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

        request.getRequestDispatcher("mypage.jsp").forward(request, response);
        */

        /*
        List accounts = new ArrayList();
        try {
            accounts = new AccountDAO().getAllAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allAccounts", accounts);

        request.getRequestDispatcher("mypage.jsp").forward(request, response);
        */

        List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("allClients", clients);

        request.getRequestDispatcher("mypage.jsp").forward(request, response);


    }





    public static void main(String[] args) {
        /*Account account = null;
        try {
            account = new AccountDAO().getAccountByID(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String accountToString = account.getClientID() + ", на счету " + account.getBalance();
        System.out.println(accountToString);*/
        System.out.println(Gender.valueOf("m").genderAsString());
        /*List clients = new ArrayList();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < clients.size(); i++) {
            Client client = (Client) clients.get(i);
            System.out.println(client.getFullName());
            System.out.println(client.getGender());
        }*/


    }

}
