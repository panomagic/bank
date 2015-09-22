<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="updateuserpagetitle" /></title>
</head>
<body>
  <h2><fmt:message key="userupdate" /></h2>
  <form name="updateuser" action="updatedeleteuser" method="POST">
    <p><b><fmt:message key="enterusername" /></b><br>
      <input type="text" name="username" value="${user.userName}" size="50">
    </p>
    <p><b><fmt:message key="enterpassword" /></b><br>
      <input type="text" name="psw" value="${user.psw}" size="50">
    </p>
    <p><b><fmt:message key="chooseclient" /></b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.id}"
            <c:choose>
              <c:when test="${client.id == user.clientID}">
                selected="selected">
              </c:when>
              <c:otherwise>
                >
              </c:otherwise>
            </c:choose>
            ${client.fullName}
          </option>
        </c:forEach>
      </select>
    </p>
    <p><b><fmt:message key="chooseuserrole" /></b><br>
      <c:choose>
        <c:when test="${user.role == 'ADMINISTRATOR'}">
          <input type="radio" name="role" value="a" checked> administrator<br>
          <input type="radio" name="role" value="c"> client<br>
        </c:when>
        <c:when test="${user.role == 'CLIENT'}">
          <input type="radio" name="role" value="a"> administrator<br>
          <input type="radio" name="role" value="c" checked> client<br>
        </c:when>
      </c:choose>
    </p>
    <p><input type="submit" value="<fmt:message key="savebutton" />">
      <input type="reset" value="<fmt:message key="cancelbutton" />"></p>
  </form>
  <p><a href="admin"><fmt:message key="returntoadminpagelink" /></a></p>
</body>
</html>