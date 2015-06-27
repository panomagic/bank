<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="m" uri="mytags" %>

<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
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
          <c:forEach var="client" items="${allClients}">
              <tr>
                  <td width="30" align="center">${client.clientID}</td>
                  <td width="150">${client.fullName}</td>
                  <td width="70" align="center">${fn:toLowerCase(client.gender)}</td>
                  <td width="100" align="center"><fmt:formatDate value="${client.dateOfBirth}" pattern="dd.MM.yyyy" /></td>
                  <td width="100" align="center"><fmt:formatDate value="${client.dateOfReg}" pattern="dd.MM.yyyy" /></td>
                  <td width="90" align="center">
                      <a href="updatedeleteclient?action=update&clientID=${client.clientID}">Изменить</a>
                  </td>
                  <td width="90" align="center">
                      <a href="updatedeleteclient?action=delete&clientID=${client.clientID}">Удалить</a>
                  </td>
              </tr>
          </c:forEach>
      </table>
  <p>
      <a href="addclient">Добавить клиента</a><br>
      <a href="viewaccounts">Перейти к списку счетов</a>
  </p>
  <p>
      <a href="addtransaction">Сделать перевод денег</a>
  </p>
  <p>
      <a href="logout">Выйти из системы</a>
  </p>

  <m:clientInfo number="4"></m:clientInfo>

</body>
</html>