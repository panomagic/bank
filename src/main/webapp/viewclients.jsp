<%@ page import="bean.Client" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Клиенты</title>
</head>
<body>
  <h2>
      Таблица клиентов банка
  </h2>
          <table border="1">
              <tr>
                  <th><b>ID</b></th>
                  <th><b>Полное имя</b></th>
                  <th><b>Пол</b></th>
                  <th><b>Дата рождения</b></th>
                  <th><b>Дата регистрации</b></th>
                  <th colspan=2><b>Действие</b></th>
              </tr>
              <% List clients = (List) request.getAttribute("allClients");
                  for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
                      Client client = (Client) iterator.next();
              %>
              <tr>
                  <td width="30"><%= client.getClientID() %></td>
                  <td width="150"><%= client.getFullName() %></td>
                  <td width="70"><%= client.getGender().genderAsString() %></td>
                  <td width="100"><%= client.getDateOfBirth() %></td>
                  <td width="100"><%= client.getDateOfReg() %></td>
                  <td width="90">
                      <a href="updatedeleteclient?action=edit&clientID=<%= client.getClientID() %>">Изменить</a>
                  </td>
                  <td width="90">
                      <a href="updatedeleteclient?action=delete&clientID=<%= client.getClientID() %>">Удалить</a>
                  </td>
              </tr>
              <% } %>
          </table>
  <p><a href="addclient">Добавить клиента</a><br>
  <a href="viewaccounts">Перейти к списку счетов</a></p>
</body>
</html>
