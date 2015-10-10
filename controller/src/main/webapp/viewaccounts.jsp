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
  <title><fmt:message key="viewaccountspagetitle" /></title>
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
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="admin">Bank Web-Application</a>
        </div>
        <div>
          <ul class="nav navbar-nav">
            <li><a href="viewclients"><fmt:message key="gotoclientslistlink" /></a></li>
            <li class="active"><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a></li>
            <li><a href="transactionshistory"><fmt:message key="gototranshistorylink" /></a></li>
            <li><a href="viewusers"><fmt:message key="gotouserslistlink" /></a></li>
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
      <h2><fmt:message key="acctable" /></h2>
      <table class="table table-condensed table-hover">
        <thead>
          <tr>
            <th><b><fmt:message key="accidcolumn" /></b></th>
            <th><b><fmt:message key="ownercolumn" /></b></th>
            <th><b><fmt:message key="acctype" /></b></th>
            <th><b><fmt:message key="currency" /></b></th>
            <th><b><fmt:message key="balance" /></b></th>
            <th colspan="2" class="text-center"><b><fmt:message key="actioncolumn" /></b></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="account" items="${allAccounts}">
            <tr>
              <td width="90">${account.id}</td>
              <td>
                <c:forEach var="client" items="${allClients}">
                  <c:choose>
                    <c:when test="${account.clientID == client.id}">
                      ${client.fullName}
                    </c:when>
                  </c:choose>
                </c:forEach>
              </td>
              <td>
                <c:choose>
                  <c:when test="${account.accTypeID == 1}">
                    DEBIT
                  </c:when>
                  <c:when test="${account.accTypeID == 2}">
                    CREDIT
                  </c:when>
                </c:choose>
              </td>
              <td>
                <c:forEach var="currencyName" items="${allCurrencies}">
                  <c:choose>
                    <c:when test="${account.currencyID == currencyName.id}">
                      ${currencyName.currencyName}
                    </c:when>
                  </c:choose>
                </c:forEach>
              </td>
              <td>${account.balance}</td>
              <td align="center">
                <a href="updatedeleteaccount?action=update&id=${account.id}"><fmt:message key="actionupdate" /></a>
              </td>
              <td align="center">
                <a href="updatedeleteaccount?action=delete&id=${account.id}"><fmt:message key="actiondelete" /></a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>