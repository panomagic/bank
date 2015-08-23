<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="viewclientspagetitle" /></title>
</head>
<body>
  <h2>
      <fmt:message key="clientstable" />
  </h2>
  <p><img src="image" /></p>
      <table border="1">
          <tr>
              <th><b><fmt:message key="clientidcolumn" /></b></th>
              <th><b><fmt:message key="fullnamecolumn" /></b></th>
              <th><b><fmt:message key="gender" /></b></th>
              <th><b><fmt:message key="dateofbirthcolumn" /></b></th>
              <th><b><fmt:message key="dateofregcolumn" /></b></th>
              <th colspan=2><b><fmt:message key="actioncolumn" /></b></th>
          </tr>
          <c:forEach var="client" items="${allClients}">
              <tr>
                  <td width="30" align="center">${client.id}</td>
                  <td width="150">${client.fullName}</td>
                  <td width="70" align="center">${fn:toLowerCase(client.gender)}</td>
                  <td width="100" align="center"><fmt:formatDate value="${client.dateOfBirth}" pattern="dd.MM.yyyy" /></td>
                  <td width="100" align="center"><fmt:formatDate value="${client.dateOfReg}" pattern="dd.MM.yyyy" /></td>
                  <td width="90" align="center">
                      <a href="updatedeleteclient?action=update&id=${client.id}"><fmt:message key="actionupdate" /></a>
                  </td>
                  <td width="90" align="center">
                      <a href="updatedeleteclient?action=delete&id=${client.id}"><fmt:message key="actiondelete" /></a>
                  </td>
              </tr>
          </c:forEach>
      </table>
  <p>
      <a href="addclient"><fmt:message key="addclientlink" /></a><br>
      <a href="viewaccounts"><fmt:message key="gotoacclistlink" /></a>
  </p>
  <p>
      <a href="addtransaction"><fmt:message key="addtransactionlink" /></a>
  </p>
  <p>
      <a href="logout"><fmt:message key="logoutlink" /></a>
  </p>
</body>
</html>