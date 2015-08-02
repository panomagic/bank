<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="DeleteclientBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="pagetitle" /></title>
</head>
  <body>
    <p><fmt:message key="clientdelsuccessfull" /></p>
    <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a><br>
    <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a>
  </body>
</html>