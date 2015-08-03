<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
  <head>
    <meta charset="utf-8">
    <title><fmt:message key="deleteaccountpagetitle" /></title>
  </head>
  <body>
    <p><b><fmt:message key="accdelsuccessfull" /></b></p>
    <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
    <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
  </body>
</html>