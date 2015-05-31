<%@ page import="bean.Client" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.AccountDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
    <title>Создание транзакции</title>
</head>
<body>
  <h2>Произвести перевод денег</h2>
  <form name="addaccount" method="POST">
    <p><b>Выберите отправителя:</b><br>
      <select name="choosepayer">
        <% List clients = (List) request.getAttribute("allClients");
          for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
        %>
        <option value="<%= client.getClientID() %>"><%= client.getFullName() %></option>
        <% } %>
      </select>

      p><b>Выберите счет отправителя:</b><br>
      <select name="choosepayeraccount">
      <% List accounts = new ArrayList();
      try {
        accounts = new AccountDAO().getAccountsByClientID(cl);
      } catch (SQLException e) {
      e.printStackTrace();
      } %>
    </p>

    <p><b>Выберите получателя:</b><br>
      <select name="chooseclient">
        <% List clients2 = (List) request.getAttribute("allClients");
          for (Iterator iterator = clients2.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
        %>
        <option value="<%= client.getClientID() %>"><%= client.getFullName() %></option>
        <% } %>
      </select>
    </p>



    <p><b>Тип счета:</b><br>
      <input type="radio" name="acctypeID" value="1"> Debit<br>
      <input type="radio" name="acctypeID" value="2"> Credit<br>

      <%--<select name="choosecurrency">
        <% List currencies = (List) request.getAttribute("allCurrencies");
          for (Iterator iterator = currencies.iterator(); iterator.hasNext(); ) {
            Currency currency = (Currency) iterator.next();
        %>
        <option><%= currency.getCurrency() %></option>
        <% } %>
      </select>--%>
    </p>

    <p><b>Валюта:</b><br>
      <input type="radio" name="currencyID" value="1"> UAH<br>
      <input type="radio" name="currencyID" value="2"> USD<br>
      <input type="radio" name="currencyID" value="3"> EUR<br>
    </p>

    <p><input type="submit" value="Сохранить">
      <input type="reset" value="Очистить"></p>
  </form>
  <p>
    <a href="viewaccounts">Вернуться к списку счетов</a><br>
    <a href="viewclients">Вернуться к списку клиентов</a>
  </p>

</body>
</html>
