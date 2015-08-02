<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="UpdateclientBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="pagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="clientupdate" /></h2>
<form name="updateclient" action="updatedeleteclient" method="POST">
  <p><b><fmt:message key="clientfullname" /></b><br>
    <input type="text" name="fullname" value="${client.fullName}" size="50">
  </p>
  <p><b><fmt:message key="gender" /></b><br>
    <c:choose>
      <c:when test="${client.gender == 'MALE'}">
        <input type="radio" name="gender" value="m" checked> <fmt:message key="gendermale" /><br>
        <input type="radio" name="gender" value="f"> <fmt:message key="genderfemale" /><br>
      </c:when>
      <c:otherwise>
        <input type="radio" name="gender" value="m"> <fmt:message key="gendermale" /><br>
        <input type="radio" name="gender" value="f" checked> <fmt:message key="genderfemale" /><br>
      </c:otherwise>
    </c:choose>
  </p>
  <p><b><fmt:message key="dateofbirth" /></b><br>
    <input type="date" name="dateofbirth"
           value="<fmt:formatDate value="${client.dateOfBirth}" pattern="dd.MM.yyyy" />"></p>
  <p><b><fmt:message key="dateofreg" /></b><br>
    <input type="date" name="dateofreg"
           value="<fmt:formatDate value="${client.dateOfReg}" pattern="dd.MM.yyyy" />"></p>
  <p><input type="submit" value="<fmt:message key="savebutton" />">
    <input type="reset" value="<fmt:message key="cancelbutton" />"></p>
</form>
<p><a href="viewclients"><fmt:message key="gotoclientslistbutton" /></a></p>
</body>
</html>