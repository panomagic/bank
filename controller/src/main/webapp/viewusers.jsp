<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="viewuserspagetitle" /></title>
  <title><fmt:message key="viewaccountspagetitle" /></title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="resources/css/bootstrap.min.css">
  <!-- Optional theme -->
  <link rel="stylesheet" href="resources/css/bootstrap-theme.min.css">
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <!-- Latest compiled JavaScript -->
  <script src="resources/js/bootstrap.min.js"></script>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->
</head>
  <body>
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="admin">Bank Web-Application</a>
        </div>
        <div>
          <ul class="nav navbar-nav">
            <li><a href="viewclients"><fmt:message key="gotoclientslistlink" /></a></li>
            <li><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a></li>
            <li><a href="transactionshistory"><fmt:message key="gototranshistorylink" /></a></li>
            <li class="active"><a href="viewusers"><fmt:message key="gotouserslistlink" /></a></li>
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <fmt:message key="addmenuitem" />
                <span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li><a href="addclient"><fmt:message key="addclientlink" /></a></li>
                <li><a href="addaccount"><fmt:message key="addaccountlink" /></a></li>
                <li><a href="addtransaction"><fmt:message key="addtransactionlink" /></a></li>
                <li><a href="upload"><fmt:message key="addimagelink" /></a></li>
                <li><a href="adduser"><fmt:message key="gotoadduserlink" /></a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logoutlink" /></a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container">
      <p><img src="image" /></p>
      <h2><fmt:message key="userstable" /></h2>

      <table class="table table-condensed table-hover" style="width: auto;">
        <thead>
          <tr>
            <th><b><fmt:message key="useridcolumn" /></b></th>
            <th><b><fmt:message key="usernamecolumn" /></b></th>
            <th><b><fmt:message key="fullnamecolumn" /></b></th>
            <th><b><fmt:message key="userrole" /></b></th>
            <th colspan=2 class="text-center"><b><fmt:message key="actioncolumn" /></b></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="user" items="${allUsers}">
            <tr>
              <td>${user.id}</td>
              <td>${user.userName}</td>
              <td>
                <c:forEach var="client" items="${allClients}">
                  <c:choose>
                    <c:when test="${user.clientID == client.id}">
                      ${client.fullName}
                    </c:when>
                  </c:choose>
                </c:forEach>
              </td>
              <td>
                <c:choose>
                  <c:when test="${user.role == 'ADMINISTRATOR'}">
                    ADMINISTRATOR
                  </c:when>
                  <c:when test="${user.role == 'CLIENT'}">
                    CLIENT
                  </c:when>
                </c:choose>
              </td>
              <td>
                <a href="updatedeleteuser?action=update&id=${user.id}"><fmt:message key="actionupdate" /></a>
              </td>
              <td>
                <a href="updatedeleteuser?action=delete&id=${user.id}"><fmt:message key="actiondelete" /></a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>