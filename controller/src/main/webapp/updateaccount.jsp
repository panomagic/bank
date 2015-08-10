<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="updateaccountpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="accupdate" /></h2>
  <form name="updateaccount" action="updatedeleteaccount" method="POST">
    <p><b><fmt:message key="chooseclient" /></b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.id}"
            <c:choose>
              <c:when test="${client.id == account.clientID}">
                selected="selected">
              </c:when>
              <c:otherwise>
                >
              </c:otherwise>
            </c:choose>
            ${client.fullName}
          </option>
        </c:forEach>
      </select>
    </p>

    <p><b><fmt:message key="acctype" /></b><br>
      <c:choose>
        <c:when test="${account.accTypeID == 1}">
          <input type="radio" name="acctypeID" value="1" checked> DEBIT<br>
          <input type="radio" name="acctypeID" value="2"> CREDIT<br>
        </c:when>
        <c:when test="${account.accTypeID == 2}">
          <input type="radio" name="acctypeID" value="1"> DEBIT<br>
          <input type="radio" name="acctypeID" value="2" checked> CREDIT<br>
        </c:when>
      </c:choose>
    </p>

    <p><b><fmt:message key="currency" /></b><br>
      <c:choose>
        <c:when test="${account.currencyID == 1}">
          <input type="radio" name="currencyID" value="1" checked> UAH<br>
          <input type="radio" name="currencyID" value="2"> USD<br>
          <input type="radio" name="currencyID" value="3"> EUR<br>
        </c:when>
        <c:when test="${account.currencyID == 2}">
          <input type="radio" name="currencyID" value="1"> UAH<br>
          <input type="radio" name="currencyID" value="2" checked> USD<br>
          <input type="radio" name="currencyID" value="3"> EUR<br>
        </c:when>
        <c:when test="${account.currencyID == 3}">
          <input type="radio" name="currencyID" value="1"> UAH<br>
          <input type="radio" name="currencyID" value="2"> USD<br>
          <input type="radio" name="currencyID" value="3" checked> EUR<br>
        </c:when>
      </c:choose>
    </p>

    <p><input type="submit" value="<fmt:message key="savebutton" />">
      <input type="reset" value="<fmt:message key="cancelbutton" />"></p>
  </form>
  <p><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a></p>
</body>
</html>