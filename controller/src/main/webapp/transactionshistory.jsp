<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="transactionshistorypagetitle" /></title>
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
      <c:set var="userrole" value="${userrole}"/>
      <c:choose>
        <c:when test="${userrole == 'ADMINISTRATOR'}">
          <div class="navbar-header">
            <a class="navbar-brand" href="admin">Bank Web-Application</a>
          </div>
          <div>
            <ul class="nav navbar-nav">
              <li><a href="viewclients"><fmt:message key="gotoclientslistlink" /></a></li>
              <li><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a></li>
              <li class="active"><a href="transactionshistory"><fmt:message key="gototranshistorylink" /></a></li>
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
        </c:when>
        <c:when test="${userrole == 'CLIENT'}">
          <div class="navbar-header">
            <a class="navbar-brand" href="/clientinfo">Bank Web-Application</a>
          </div>
          <div>
            <ul class="nav navbar-nav">
              <li><a href="addtransaction"><fmt:message key="addtransaction" /></a></li>
              <li class="active"><a href="transactionshistorybyclient"><fmt:message key="transhistorybyclientlink" /></a></li>
              <li><a href="upload"><fmt:message key="addimagelink" /></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logoutlink" /></a></li>
            </ul>
          </div>
        </c:when>
      </c:choose>
    </div>
  </nav>

  <div class="container">
    <p><img src="image" /></p>
    <h2><fmt:message key="transhistoryheader" /></h2>
    <table class="table table-condensed table-hover">
      <thead>
        <tr>
          <th><b><fmt:message key="transtableid" /></b></th>
          <th><b><fmt:message key="payer" /></b></th>
          <th><b><fmt:message key="transtablepayeracc" /></b></th>
          <th><b><fmt:message key="transtablepayeracctype" /></b></th>
          <th><b><fmt:message key="recipient" /></b></th>
          <th><b><fmt:message key="transtablerecipientacc" /></b></th>
          <th><b><fmt:message key="transtablerecipientacctype" /></b></th>
          <th><b><fmt:message key="currency" /></b></th>
          <th><b><fmt:message key="transtabledatetime" /></b></th>
          <th><b><fmt:message key="transtableamount" /></b></th>
        </tr>
      </thead>

      <tbody>
        <c:forEach var="transaction" items="${allTransactions}">
          <tr>
            <td>
              ${transaction.id}
            </td>
            <td>
              <c:forEach var="payer" items="${allClients}">
                <c:choose>
                  <c:when test="${transaction.payerID == payer.id}">
                    ${payer.fullName}
                  </c:when>
                </c:choose>
              </c:forEach>
            </td>
            <td>
                ${transaction.payerAccID}
            </td>
            <td>
              <c:forEach var="payerAccount" items="${allAccounts}">
                <c:choose>
                  <c:when test="${transaction.payerAccID == payerAccount.id}">
                    <c:choose>
                      <c:when test="${payerAccount.accTypeID == 1}">
                        DEBIT
                      </c:when>
                      <c:when test="${payerAccount.accTypeID == 2}">
                        CREDIT
                      </c:when>
                    </c:choose>
                  </c:when>
                </c:choose>
              </c:forEach>
            </td>
            <td>
              <c:forEach var="recipient" items="${allClients}">
                <c:choose>
                  <c:when test="${transaction.recipientID == recipient.id}">
                    ${recipient.fullName}
                  </c:when>
                </c:choose>
              </c:forEach>
            </td>
            <td>
               ${transaction.recipientAccID}
            </td>
            <td>
              <c:forEach var="recipientAccount" items="${allAccounts}">
                <c:choose>
                  <c:when test="${transaction.recipientAccID == recipientAccount.id}">
                    <c:choose>
                      <c:when test="${recipientAccount.accTypeID == 1}">
                        DEBIT
                      </c:when>
                      <c:when test="${recipientAccount.accTypeID == 2}">
                        CREDIT
                      </c:when>
                    </c:choose>
                  </c:when>
                </c:choose>
              </c:forEach>
            </td>
            <td>
              <c:forEach var="currencyName" items="${allCurrencies}">
                <c:choose>
                  <c:when test="${transaction.currencyID == currencyName.id}">
                    ${currencyName.currencyName}
                  </c:when>
                </c:choose>
              </c:forEach>
            </td>
            <td>
              <fmt:formatDate value="${transaction.transDateTime}" pattern="dd.MM.yyyy HH:mm:ss" />
            </td>
            <td>
              ${transaction.sum}
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>>
</body>
</html>