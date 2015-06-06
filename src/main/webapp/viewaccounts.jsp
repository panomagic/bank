<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
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
    <c:forEach var="account" items="${allAccounts}">
      <tr>
        <td width="30" align="center">${account.accountID}</td>
        <td width="150">
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${account.clientID == client.clientID}">
                ${client.fullName}
              </c:when>
            </c:choose>
          </c:forEach>
        </td>
        <td width="100" align="center">
          <c:choose>
            <c:when test="${account.accTypeID == 1}">
              DEBIT
            </c:when>
            <c:when test="${account.accTypeID == 2}">
              CREDIT
            </c:when>
          </c:choose>
        </td>
        <td width="70" align="center">
          <c:forEach var="currency" items="${allCurrencies}">
            <c:choose>
              <c:when test="${account.currencyID == currency.currencyID}">
                ${currency.currency}
              </c:when>
            </c:choose>
          </c:forEach>
        </td>
        <td width="100" align="right">${account.balance}</td>
        <td width="90" align="center">
          <a href="updatedeleteaccount?action=update&accountID=${account.accountID}">Изменить</a>
        </td>
        <td width="90" align="center">
          <a href="updatedeleteaccount?action=delete&accountID=${account.accountID}">Удалить</a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <p><a href="addaccount">Добавить счет</a><br>
     <a href="viewclients">Вернуться к списку клиентов</a>
  </p>
  <p>
    <a href="addtransaction">Сделать перевод денег</a>
  </p>
  </body>
</html>