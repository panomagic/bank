<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="adduserpagetitle" /></title>
</head>
<body>
<h2><fmt:message key="addnewuser" /></h2>
  <form name="addaccount" method="POST">
    <p><b><fmt:message key="enterusername" /></b><br>
      <input type="text" name="userName" placeholder="Username" size="30">
    </p>
    <p><b><fmt:message key="enterpassword" /></b><br>
      <input type="text" name="psw" placeholder="Password" size="20">
    </p>
    <p><b><fmt:message key="chooseclientforuser" /></b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.id}">${client.fullName}</option>
        </c:forEach>
      </select>
    </p>
    <p><b><fmt:message key="chooseuserrole" /></b><br>
      <input type="radio" name="role" value="a"> administrator<br>
      <input type="radio" name="role" value="c"> client<br>
    </p>
    <p><input type="submit" value="<fmt:message key="savebutton" />">
      <input type="reset" value="<fmt:message key="clearbutton" />"></p>
  </form>
  <p><a href="admin"><fmt:message key="returntoadminpagelink" /></a></p>
</body>
</html>