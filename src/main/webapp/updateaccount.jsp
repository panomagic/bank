<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="bean.Account" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Изменение счета</title>
</head>
<body>
  <h2>Изменение счета</h2>
  <form name="updateaccount" action="updatedeleteaccount" method="POST">
    <% Account account = (Account) request.getAttribute("account"); %>
    <p><b>Выберите клиента:</b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.clientID}"
            <c:choose>
              <c:when test="${client.clientID == account.clientID}">
                selected="selected">
              </c:when>
              <c:otherwise>
                >
              </c:otherwise>
            </c:choose>
            ${client.fullName}
          </option>
        </c:forEach>

        <%--<% List clients = (List) request.getAttribute("allClients");
          for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
        %>
        <option value="<%= client.getClientID() %>"
              <% if(client.getClientID() == account.getClientID()) { %> selected="selected" <% } %>>
              <%= client.getFullName() %></option>
        <% } %>--%>
      </select>
    </p>

    <p><b>Тип счета:</b><br>
      <% if(account.getAccTypeID() == 1) { %>
      <input type="radio" name="acctypeID" value="1" checked> DEBIT<br>
      <input type="radio" name="acctypeID" value="2"> CREDIT<br>
      <% } else { %>
        <input type="radio" name="acctypeID" value="1"> DEBIT<br>
        <input type="radio" name="acctypeID" value="2" checked> CREDIT<br>
      <% } %>
    </p>

    <p><b>Валюта:</b><br>
      <% if(account.getCurrencyID() == 1) { %>
      <input type="radio" name="currencyID" value="1" checked> UAH<br>
      <% } else { %>
      <input type="radio" name="currencyID" value="1"> UAH<br>
      <% }
      if(account.getCurrencyID() == 2) { %>
      <input type="radio" name="currencyID" value="2" checked> USD<br>
      <% } else { %>
      <input type="radio" name="currencyID" value="2"> USD<br>
      <% }
      if(account.getCurrencyID() == 3) { %>
      <input type="radio" name="currencyID" value="3" checked> EUR<br>
        <% } else { %>
      <input type="radio" name="currencyID" value="3"> EUR<br>
      <% } %>
    </p>

    <p><input type="submit" value="Сохранить изменения">
      <input type="reset" value="Отменить ввод"></p>
  </form>
  <p><a href="viewaccounts">Назад к списку счетов</a></p>
</body>
</html>