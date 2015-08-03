<%@ taglib prefix="m" uri="mytags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <title><fmt:message key="clientpagetitle" /></title>
</head>
<body>
<h2>
  <fmt:message key="clientloginsuccessfull" />
</h2>
<h2>
  <fmt:message key="youracclist" />
</h2>
  <m:clientInfo></m:clientInfo>
<p>
  <a href="addtransaction"><fmt:message key="addtransaction" /></a>
</p>
<p>
  <a href="transactionshistorybyclient"><fmt:message key="transhistorybyclientlink" /></a>
</p>
<p>
  <a href="logout"><fmt:message key="logoutlink" /></a>
</p>
</body>
</html>