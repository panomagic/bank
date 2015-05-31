<%@ page import="bean.Client" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
    <title>Создание транзакции</title>
</head>
<body>
  <h2>Произвести перевод денежных средств</h2>
  <form name="addtransaction" method="POST">
    <p><b>Выберите счет отправителя:</b><br>
      <select name="choosepayeraccount">
        <% List accounts = (List) request.getAttribute("allAccounts");
          for (Iterator iterator = accounts.iterator(); iterator.hasNext(); ) {
            Account account = (Account) iterator.next();
            Client client = null;
            Currency currency = null;
            try {
              client = new ClientDAO().getClientByAccountID(account.getAccountID());
              currency = new CurrencyDAO().getCurrencyByID(account.getCurrencyID());
            } catch (SQLException e) {
              e.printStackTrace();
            }
            String accountType;
            if(account.getAccTypeID() == 1)
              accountType = "DEBIT";
            else accountType = "CREDIT";
        %>
        <option value="<%= account.getAccountID() %>">
            <%= client.getFullName() + "  |  " + accountType + "  |  " + currency.getCurrency() %></option>
        <% } %>
      </select>
    </p>

    <p><b>Выберите счет получателя:</b><br>
      <select name="chooserecipientaccount">
        <% List accounts2 = (List) request.getAttribute("allAccounts");
          for (Iterator iterator = accounts2.iterator(); iterator.hasNext(); ) {
            Account account = (Account) iterator.next();
            Client client = null;
            Currency currency = null;
            try {
              client = new ClientDAO().getClientByAccountID(account.getAccountID());
              currency = new CurrencyDAO().getCurrencyByID(account.getCurrencyID());
            } catch (SQLException e) {
              e.printStackTrace();
            }
            String accountType;
            if(account.getAccTypeID() == 1)
              accountType = "DEBIT";
            else accountType = "CREDIT";
        %>
          <option value="<%= account.getAccountID() %>">
            <%= client.getFullName() + "  |  " + accountType + "  |  " + currency.getCurrency() %></option>
            <% } %>
      </select>
    </p>


    <p><b>Введите сумму:</b><br>
      <input type="number" size="9" name="sum" min="0.01" value="0.00" step="0.01">
    </p>

    <p><input type="submit" value="Отправить">
      <input type="reset" value="Очистить"></p>
  </form>
  <p>
    <a href="viewaccounts">Вернуться к списку счетов</a><br>
    <a href="viewclients">Вернуться к списку клиентов</a>
  </p>

</body>
</html>
