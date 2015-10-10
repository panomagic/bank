<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="accessdeniedpagetitle" /></title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <!-- Optional theme -->
  <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <!-- Latest compiled JavaScript -->
  <script src="../js/bootstrap.min.js"></script>
</head>
<body>
  <div class="alert alert-danger">
    <h2><fmt:message key="onlyadminallowed" /></h2>
    <a href="clientinfo"><fmt:message key="clientinfolink" /></a>
  </div>
</body>
</html>