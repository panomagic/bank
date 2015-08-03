<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <title><fmt:message key="addaccountresultpagetitle" /></title>
</head>
<body>
  <b><fmt:message key="accountsuccess" /></b><br>
  <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
  <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
</body>
</html>