<%@ page import="bean.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Счета</title>
</head>
  <body>
    <h2>
      Таблица счетов, открытых клиентами банка
    </h2>
  <table border="1">
    <tr>
      <th><b>ID счета</b></th>
      <th><b>Владелец</b></th>
      <th><b>Тип счета</b></th>
      <th><b>Валюта</b></th>
      <th><b>Баланс счета</b></th>
      <th colspan=2><b>Действие</b></th>
    </tr>
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
    <tr>
      <td width="30" align="center"><%= account.getAccountID() %></td>
      <td width="150"><%= client.getFullName() %></td>
      <td width="100" align="center"><%= accountType %></td>
      <td width="70" align="center"><%= currency.getCurrency() %></td>
      <td width="100" align="right"><%= account.getBalance() %></td>
      <td width="90" align="center">
        <a href="updatedeleteaccount?action=update&accountID=<%= account.getAccountID() %>">Изменить</a>
      </td>
      <td width="90" align="center">
        <a href="updatedeleteaccount?action=delete&accountID=<%= account.getAccountID() %>">Удалить</a>
      </td>
    </tr>
    <% } %>
  </table>
  <p><a href="addaccount">Добавить счет</a><br>
     <a href="viewclients">Вернуться к списку клиентов</a>
  </p>
  <p>
    <a href="addtransaction">Сделать перевод денег</a>
  </p>
  </body>
</html>