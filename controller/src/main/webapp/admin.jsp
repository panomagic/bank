<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="adminpagetitle" /></title>
</head>
<body>
  <p><b><fmt:message key="adminloginsuccessfull" /></b></p>
  <p><img src="image" /></p>
  <a href="upload"><fmt:message key="addimagelink" /></a><br>
  <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a><br>
  <a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
  <a href="transactionshistory"><fmt:message key="gototranshistorylink" /></a>
  <p>
    <a href="logout"><fmt:message key="logoutlink" /></a>
  </p>
</body>
</html>