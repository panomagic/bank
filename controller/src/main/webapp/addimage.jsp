<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="addimagepagetitle" /></title>
</head>
<body>
<p><b><fmt:message key="adminloginsuccessfull" /></b></p>
<p><img src="image" /></p>
<form action="upload" method="post" enctype="multipart/form-data">
  <input type="text" name="description" />
  <input type="file" name="file" /><br>
  <input type="submit" value="Загрузить" />
</form>
<p>
<a href="viewclients"><fmt:message key="gotoclientslistlink" /></a><br>
<a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
<a href="transactionshistory"><fmt:message key="gototranshistorylink" /></a>
</p>
</body>
</html>