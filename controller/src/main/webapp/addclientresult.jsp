<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
    <title><fmt:message key="addclientresultpagetitle" /></title>
</head>
<body>
  <b><fmt:message key="clientsuccess" /></b><br>
  <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a><br>
  <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
</body>
</html>