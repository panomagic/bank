<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
    <title><fmt:message key="addtransactionpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="addnewtransaction" /></h2>
  <form name="addtransaction" method="POST">
    <p><b><fmt:message key="choosepayeraccount" /></b><br>
      <select name="choosepayeraccount">
        <c:forEach var="account" items="${payerAccounts}">
          <c:choose>
            <c:when test="${account.accTypeID == 1}">
              <c:set var="accType" value="DEBIT"/>
            </c:when>
            <c:when test="${account.accTypeID == 2}">
              <c:set var="accType" value="CREDIT"/>
            </c:when>
          </c:choose>
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${account.clientID == client.id}">
                <c:forEach var="currencyName" items="${allCurrencies}">
                  <c:choose>
                    <c:when test="${account.currencyID == currencyName.id}">
                      <option value="${account.id}">
                        ${client.fullName} | ${accType} | ${account.balance} ${currencyName.currencyName}
                      </option>
                    </c:when>
                  </c:choose>
                </c:forEach>
              </c:when>
            </c:choose>
          </c:forEach>
        </c:forEach>
      </select>
    </p>

    <p><b><fmt:message key="chooserecipientaccount" /></b><br>
      <select name="chooserecipientaccount">
        <c:forEach var="account" items="${recipientAccounts}">
          <c:choose>
            <c:when test="${account.accTypeID == 1}">
              <c:set var="accType" value="DEBIT"/>
            </c:when>
            <c:when test="${account.accTypeID == 2}">
              <c:set var="accType" value="CREDIT"/>
            </c:when>
          </c:choose>
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${account.clientID == client.id}">
                <c:forEach var="currencyName" items="${allCurrencies}">
                  <c:choose>
                    <c:when test="${account.currencyID == currencyName.id}">
                      <option value="${account.id}">
                          ${client.fullName} | ${accType} | ${account.balance} ${currencyName.currencyName}
                      </option>
                    </c:when>
                  </c:choose>
                </c:forEach>
              </c:when>
            </c:choose>
          </c:forEach>
        </c:forEach>
      </select>
    </p>

    <p>
      <b><fmt:message key="entersum" /></b><br>
      <input type="number" size="9" name="sum" min="0.01" value="0.00" step="0.01">
    </p>

    <p>
      <input type="submit" value="<fmt:message key="savebutton" />">
      <input type="reset" value="<fmt:message key="clearbutton" />">
    </p>
  </form>
  <p>
    <c:set var="userrole" value="${userrole}"/>
    <c:choose>
      <c:when test="${userrole == 'ADMINISTRATOR'}">
        <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
        <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
      </c:when>
      <c:when test="${userrole == 'CLIENT'}">
        <a href="clientinfo"><fmt:message key="gotoaccountslistlink" /></a><br>
      </c:when>
    </c:choose>
  </p>
</body>
</html>