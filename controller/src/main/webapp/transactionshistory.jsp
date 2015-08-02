<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="TransactionshistoryBundle" />

<html>
<head>
  <title><fmt:message key="pagetitle" /></title>
</head>
<body>
<h2>
  <title><fmt:message key="transhistoryheader" /></title>
</h2>
<table border="1">
  <tr>
    <th><b><fmt:message key="transtableid" /></b></th>
    <th><b><fmt:message key="transtablepayer" /></b></th>
    <th><b><fmt:message key="transtablepayeracc" /></b></th>
    <th><b><fmt:message key="transtablepayeracctype" /></b></th>
    <th><b><fmt:message key="transtablerecipient" /></b></th>
    <th><b><fmt:message key="transtablerecipientacc" /></b></th>
    <th><b><fmt:message key="transtablerecipientacctype" /></b></th>
    <th><b><fmt:message key="transtablecurrency" /></b></th>
    <th><b><fmt:message key="transtabledatetime" /></b></th>
    <th><b><fmt:message key="transtableamount" /></b></th>
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
<c:choose>
  <c:when test="${sessionScope.LOGGED_USER.role == 'ADMINISTRATOR'}">
    <p>
      <a href="addaccount"><fmt:message key="addaccountlink" /></a>
    </p>
    <p>
      <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
    </p>
  </c:when>
  <c:when test="${sessionScope.LOGGED_USER.role == 'CLIENT'}">
    <p>
      <a href="viewaccountbyid"><fmt:message key="gotoaccountslistlink" /></a>
    </p>
  </c:when>
</c:choose>
<p>
  <a href="addtransaction"><fmt:message key="addtransactionlink" /></a>
</p>
<p>
  <a href="logout"><fmt:message key="logoutlink" /></a>
</p>
</body>
</html>