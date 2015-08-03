<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="accessdeniedpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="onlyadminallowed" /></h2>
  <a href="viewaccountbyid"><fmt:message key="viewaccountbyidlink" /></a>
</body>
</html>