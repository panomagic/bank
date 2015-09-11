<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="transoverdraftpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="transoverdrafterror" /></h2>
  <c:choose>
    <c:when test="${sessionScope.LOGGED_USER.role == 'ADMINISTRATOR'}">
      <p>
        <a href="addtransaction"><fmt:message key="tryagainlink" /></a><br>
        <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a>
      </p>
    </c:when>
    <c:when test="${sessionScope.LOGGED_USER.role == 'CLIENT'}">
      <p>
        <a href="addtransaction"><fmt:message key="tryagainlink" /></a><br>
      </p>
    </c:when>
  </c:choose>
</body>
</html>