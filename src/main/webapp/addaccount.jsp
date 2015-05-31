<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bean.Client" %>
<%@ page import="bean.Currency" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Ввод нового счета</title>
</head>
<body>
<h2>Добавление нового счета</h2>
  <form name="addaccount" method="POST">
    <p><b>Выберите клиента:</b><br>
      <select name="chooseclient">
        <% List clients = (List) request.getAttribute("allClients");
          for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
        %>
        <option value="<%= client.getClientID() %>"><%= client.getFullName() %></option>
        <% } %>
      </select>
    </p>

    <p><b>Тип счета:</b><br>
      <input type="radio" name="acctypeID" value="1"> Debit<br>
      <input type="radio" name="acctypeID" value="2"> Credit<br>
    </p>

    <p><b>Валюта:</b><br>
      <input type="radio" name="currencyID" value="1"> UAH<br>
      <input type="radio" name="currencyID" value="2"> USD<br>
      <input type="radio" name="currencyID" value="3"> EUR<br>
    </p>

    <p><input type="submit" value="Сохранить">
      <input type="reset" value="Очистить"></p>
  </form>
  <p><a href="viewaccounts">Назад к списку счетов</a></p>
</body>
</html>