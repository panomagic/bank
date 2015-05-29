package servlet;

import bean.Account;
import bean.Client;
import dao.AccountDAO;
import dao.ClientDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateDeleteAccount extends HttpServlet {
    Account account = new Account();

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        if(request.getParameter("action").equals("update")) {
            forwardPage = "updateclient.jsp";
            account.setClientID(Integer.parseInt(request.getParameter("clientID")));



            try {
                new AccountDAO().updateAccount(account);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(request.getParameter("action").equals("delete")) {
            forwardPage = "deleteaccount.jsp";
            account.setAccountID(Integer.parseInt(request.getParameter("accountID")));
            try {
                new AccountDAO().deleteAccount(account);
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
