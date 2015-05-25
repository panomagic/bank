<%@ page import="bean.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

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
    <th><b>ID типа счета</b></th>
    <th><b>ID валюты</b></th>
    <th><b>Баланс счета</b></th>
  </tr>
  <% List accounts = (List) request.getAttribute("allAccounts");
    for (Iterator iterator = accounts.iterator(); iterator.hasNext(); ) {
      Account account = (Account) iterator.next();
      Client client = new ClientDAO().getClientByAccountID(account.getAccountID());
  %>
  <tr>
    <td width="30"><%= account.getAccountID() %></td>
    <td width="150"><%= client.getFullName() %></td>
    <td width="100"><%= account.getAccTypeID() %></td>
    <td width="70"><%= account.getCurrencyID() %></td>
    <td width="100"><%= account.getBalance() %></td>
  </tr>
  <% } %>
</table>
</body>
</html>
