<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>История транзакций</title>
</head>
<body>
<h2>
  История транзакций
</h2>
<table border="1">
  <tr>
    <th><b>ID транзакции</b></th>
    <th><b>Плательщик</b></th>
    <th><b>Номер счета плательщика</b></th>
    <th><b>Тип счета плательщика</b></th>
    <th><b>Получатель</b></th>
    <th><b>Номер счета плательщика</b></th>
    <th><b>Тип счета получателя</b></th>
    <th><b>Валюта</b></th>
    <th><b>Дата и время транзакции</b></th>
    <th><b>Сумма</b></th>
  </tr>
  <c:forEach var="transaction" items="${allTransactions}">
    <tr>
      <td width="30" align="center">
        ${transaction.transID}
      </td>
      <td width="150">
        <c:forEach var="payer" items="${allClients}">
          <c:choose>
            <c:when test="${transaction.payerID == payer.clientID}">
              ${payer.fullName}
            </c:when>
          </c:choose>
        </c:forEach>
      </td>
      <td width="30" align="center">
          ${transaction.payerAccID}
      </td>
      <td width="100" align="center">
        <c:forEach var="payerAccount" items="${allAccounts}">
          <c:choose>
            <c:when test="${transaction.payerAccID == payerAccount.accountID}">
              <c:choose>
                <c:when test="${payerAccount.accTypeID == 1}">
                  DEBIT
                </c:when>
                <c:when test="${payerAccount.accTypeID == 2}">
                  CREDIT
                </c:when>
              </c:choose>
            </c:when>
          </c:choose>
        </c:forEach>
      </td>
      <td width="150">
        <c:forEach var="recipient" items="${allClients}">
          <c:choose>
            <c:when test="${transaction.recipientID == recipient.clientID}">
              ${recipient.fullName}
            </c:when>
          </c:choose>
        </c:forEach>
      </td>
      <td width="30" align="center">
          ${transaction.recipientAccID}
      </td>
      <td width="100" align="center">
        <c:forEach var="recipientAccount" items="${allAccounts}">
          <c:choose>
            <c:when test="${transaction.recipientAccID == recipientAccount.accountID}">
              <c:choose>
                <c:when test="${recipientAccount.accTypeID == 1}">
                  DEBIT
                </c:when>
                <c:when test="${recipientAccount.accTypeID == 2}">
                  CREDIT
                </c:when>
              </c:choose>
            </c:when>
          </c:choose>
        </c:forEach>
      </td>
      <td width="70" align="center">
        <c:forEach var="currency" items="${allCurrencies}">
          <c:choose>
            <c:when test="${transaction.currencyID == currency.currencyID}">
              ${currency.currency}
            </c:when>
          </c:choose>
        </c:forEach>
      </td>
      <td width="150" align="center">
        <fmt:formatDate value="${transaction.transDateTime}" pattern="dd.MM.yyyy HH:mm:ss" />
      </td>
      <td width="100" align="right">
        ${transaction.sum}
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
<p>
  <a href="logout">Выйти из системы</a>
</p>
</body>
</html>