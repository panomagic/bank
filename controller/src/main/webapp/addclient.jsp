<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="AddclientBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="pagetitle" /></title>
</head>
<body>
<h2><fmt:message key="addnewclient" /></h2>
<form name="addclient" method="POST">
  <p><b><fmt:message key="clientfullname" /></b><br>
    <input type="text" name="fullname" placeholder="ФИО" size="50">
  </p>
  <p><b><fmt:message key="gender" /></b><br>
    <input type="radio" name="gender" value="m"> <fmt:message key="gendermale" /><br>
    <input type="radio" name="gender" value="f"> <fmt:message key="genderfemale" /><br>
  </p>
  <p><b><fmt:message key="birthdate" /></b><br>
    <input type="date" name="dateofbirth"></p>
  <p><b><fmt:message key="regdate" /></b><br>
    <input type="date" name="dateofreg"></p>
  <p><input type="submit" value="<fmt:message key="savebutton" />">
    <input type="reset" value="<fmt:message key="clearbutton" />"></p>
</form>
<p><a href="viewclients"><fmt:message key="backtoclientslistlink" /></a></p>
</body>
</html>