<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="transoverdraftpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="transoverdrafterror" /></h2>
  <p>
    <a href="addtransaction"><fmt:message key="tryagainlink" /></a><br>
    <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a>
  </p>
</body>
</html>