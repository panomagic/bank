<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
  <title><fmt:message key="viewuserspagetitle" /></title>
</head>
  <body>
    <h2>
      <fmt:message key="userstable" />
    </h2>
    <p><img src="image" /></p>
  <table border="1">
    <tr>
      <th><b><fmt:message key="useridcolumn" /></b></th>
      <th><b><fmt:message key="usernamecolumn" /></b></th>
      <th><b><fmt:message key="fullnamecolumn" /></b></th>
      <th><b><fmt:message key="userrole" /></b></th>
      <th colspan=2><b><fmt:message key="actioncolumn" /></b></th>
    </tr>
    <c:forEach var="user" items="${allUsers}">
      <tr>
        <td width="30" align="center">${user.id}</td>
        <td width="30">${user.userName}</td>
        <td width="150">
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${user.clientID == client.id}">
                ${client.fullName} ${client.id}
              </c:when>
            </c:choose>
          </c:forEach>
        </td>
        <td width="100" align="center">
          <c:choose>
            <c:when test="${user.role == 'ADMINISTRATOR'}">
              ADMINISTRATOR
            </c:when>
            <c:when test="${user.role == 'CLIENT'}">
              CLIENT
            </c:when>
          </c:choose>
        </td>
        <td width="90" align="center">
          <a href="updatedeleteuser?action=update&id=${user.id}"><fmt:message key="actionupdate" /></a>
        </td>
        <td width="90" align="center">
          <a href="updatedeleteuser?action=delete&id=${user.id}"><fmt:message key="actiondelete" /></a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <p><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a><br>
     <a href="viewclients"><fmt:message key="gotoclientslistlink" /></a>
  </p>
  <p>
    <a href="addtransaction"><fmt:message key="addtransactionlink" /></a>
  </p>
  <p>
    <a href="transactionshistory"><fmt:message key="transhistorylink" /></a>
  </p>
  <p>
    <a href="admin"><fmt:message key="returntoadminpagelink" /></a>
  </p>
  <p>
    <a href="logout"><fmt:message key="logoutlink" /></a>
  </p>
  </body>
</html>