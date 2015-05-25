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
              </tr>
              <% List clients = (List) request.getAttribute("allClients");
                  for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
                      Client client = (Client) iterator.next();
              %>
              <tr>
                  <td width="30"><%= client.getClientID() %></td>
                  <td width="100"><%= client.getFullName() %></td>
                  <td width="70"><%= client.getGender().genderAsString() %></td>
                  <td width="100"><%= client.getDateOfBirth() %></td>
                  <td width="100"><%= client.getDateOfReg() %></td>
              </tr>
              <% } %>
          </table>
</body>
</html>
