package servlet;
import bean.Account;
import bean.Client;
import bean.Currency;
import bean.User;
import dao.AccountDAO;
import dao.ClientDAO;
import dao.CurrencyDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientInfoServlet extends TagSupport {
    private static final Logger logger = Logger.getLogger(ClientInfoServlet.class);

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        List<Account> accounts = new ArrayList<Account>();
        try {
            accounts = new AccountDAO().getAccountsByClientID(loggedUser.getClientID());
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        List<Client> clients = new ArrayList<Client>();
        try {
            clients = new ClientDAO().getAllClients();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        List<Currency> currencies = new ArrayList<Currency>();
        try {
            currencies = new CurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        try {
            out.write("<table border='1'>");
            out.write("<tr>");
            out.write("<th><b>ID счета</b></th>");
            out.write("<th><b>Владелец</b></th>");
            out.write("<th><b>Тип счета</b></th>");
            out.write("<th><b>Валюта</b></th>");
            out.write("<th><b>Баланс счета</b></th>");

            for (int i = 0; i < accounts.size(); i++) {
                out.write("<tr>");

                out.write("<td width=\"30\" align=\"center\">" + accounts.get(i).getAccountID() + "</td>");

                out.write("<td width=\"150\">");
                for (int j = 0; j < clients.size(); j++) {
                    if (accounts.get(i).getClientID() == clients.get(j).getClientID())
                        out.write(clients.get(j).getFullName());
                }
                out.write("</td>");

                out.write("<td width=\"100\" align=\"center\">");
                if (accounts.get(i).getAccTypeID() == 1)
                    out.write("DEBIT");
                else if (accounts.get(i).getAccTypeID() == 2)
                    out.write("CREDIT");
                out.write("</td>");

                out.write("<td width=\"70\" align=\"center\">");
                for (int j = 0; j < currencies.size(); j++) {
                    if (accounts.get(i).getCurrencyID() == currencies.get(j).getCurrencyID())
                        out.write(currencies.get(j).getCurrency());
                }
                out.write("</td>");

                out.write("<td width=\"100\" align=\"right\">" + accounts.get(i).getBalance() + "</td>");

                out.write("</tr>");
            }
            out.write("</table>");
        } catch (IOException e) {
            logger.warn("IO error while processing of JSP tags", e);
        }

        return SKIP_BODY;
    }
}