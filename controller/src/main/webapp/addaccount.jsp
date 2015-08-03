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
  <title><fmt:message key="addaccountpagetitle" /></title>
</head>
<body>
<h2><fmt:message key="addnewaccount" /></h2>
  <form name="addaccount" method="POST">
    <p><b><fmt:message key="chooseclient" /></b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.clientID}">${client.fullName}</option>
        </c:forEach>
      </select>
    </p>

    <p><b><fmt:message key="acctypesemicolon" /></b><br>
      <input type="radio" name="acctypeID" value="1"> DEBIT<br>
      <input type="radio" name="acctypeID" value="2"> CREDIT<br>
    </p>

    <p><b><fmt:message key="currencysemicolon" /></b><br>
      <input type="radio" name="currencyID" value="1"> UAH<br>
      <input type="radio" name="currencyID" value="2"> USD<br>
      <input type="radio" name="currencyID" value="3"> EUR<br>
    </p>

    <p><input type="submit" value="<fmt:message key="savebutton" />">
      <input type="reset" value="<fmt:message key="clearbutton" />"></p>
  </form>
  <p><a href="viewaccounts"><fmt:message key="backtoaccountslink" /></a></p>
</body>
</html>