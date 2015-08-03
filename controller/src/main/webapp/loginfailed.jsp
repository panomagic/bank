<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="loginfailedpagetitle" /></title>
</head>
<body>
  <p><b><fmt:message key="loginerror" /></b></p>
  <form action="login" method="POST">
    <p><b><fmt:message key="username" /></b><br>
      <input type="text" name="userName" size="35">
    </p>
    <p><b><fmt:message key="password" /></b><br>
      <input type="text" name="password" size="35">
    </p>
    <p><b><fmt:message key="chooselanguage" /></b><br>
      <select name="chosenlanguage">
        <option value="en">English</option>
        <option value="ru">Русский</option>
      </select>
    </p>
    <p>
      <input type="submit" value="<fmt:message key="loginbutton" />">
      <input type="reset" value="<fmt:message key="clearbutton" />">
    </p>
  </form>
</body>
</html>