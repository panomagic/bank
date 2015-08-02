<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="ViewaccountsBundle" />

<html>
<head>
  <title><fmt:message key="pagetitle" /></title>
</head>
  <body>
    <h2>
      <fmt:message key="acctable" />
    </h2>
  <table border="1">
    <tr>
      <th><b><fmt:message key="accidcolumn" /></b></th>
      <th><b><fmt:message key="ownercolumn" /></b></th>
      <th><b><fmt:message key="acctypecolumn" /></b></th>
      <th><b><fmt:message key="currencycolumn" /></b></th>
      <th><b><fmt:message key="balancecolumn" /></b></th>
      <th colspan=2><b><fmt:message key="actioncolumn" /></b></th>
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
  <p><a href="addaccount"><fmt:message key="addaccountlink" /></a><br>
     <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
  </p>
  <p>
    <a href="addtransaction"><fmt:message key="addtransactionlink" /></a>
  </p>
  <p>
    <a href="transactionshistory"><fmt:message key="transhistorylink" /></a>
  </p>
  <p>
    <a href="logout"><fmt:message key="logoutlink" /></a>
  </p>
  </body>
</html>