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
<h3>
  <fmt:message key="youracclist" />
</h3>
<table border='1'>
  <tr>
    <th><b><fmt:message key="accid" /></b></th>
    <th><b><fmt:message key="accowner" /></b></th>
    <th><b><fmt:message key="acctype" /></b></th>
    <th><b><fmt:message key="currency" /></b></th>
    <th><b><fmt:message key="balance" /></b></th>
  </tr>
  <m:forEach items="${records}" var="row">
    <tr>
      <m:forEach items="${row}" var="col">
        <td>
            ${col}
        </td>
      </m:forEach>
    </tr>
  </m:forEach>
</table>
<p>
  <a href="addtransaction"><fmt:message key="addtransaction" /></a>
</p>
<p>
  <a href="transactionshistorybyclient"><fmt:message key="transhistorybyclientlink" /></a>
</p>
<p>
  <a href="logout"><fmt:message key="logoutlink" /></a>
</p>
<img src="image" width="100">
</body>
</html>