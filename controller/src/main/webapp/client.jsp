<%@ taglib prefix="m" uri="mytags" %>
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
  <title><fmt:message key="clientinfo" /></title>
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
        <a class="navbar-brand" href="/clientinfo">Bank Web-Application</a>
      </div>
      <div>
        <ul class="nav navbar-nav">
          <li><a href="addtransaction"><fmt:message key="addtransaction" /></a></li>
          <li><a href="transactionshistorybyclient"><fmt:message key="transhistorybyclientlink" /></a></li>
          <li><a href="upload"><fmt:message key="addimagelink" /></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logoutlink" /></a></li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container">
    <p><img src="image" /></p>
    <h2><fmt:message key="clientloginsuccessfull" /></h2>

    <h3><fmt:message key="youracclist" /></h3>
    <table class="table table-condensed table-hover" style="width: auto;">
      <thead>
        <tr>
          <th><b><fmt:message key="accid" /></b></th>
          <th><b><fmt:message key="accowner" /></b></th>
          <th><b><fmt:message key="acctype" /></b></th>
          <th><b><fmt:message key="currency" /></b></th>
          <th><b><fmt:message key="balance" /></b></th>
        </tr>
      </thead>
      <tbody>
        <m:forEach items="${records}" var="row">
          <tr>
            <m:forEach items="${row}" var="col">
              <td>
                  ${col}
              </td>
            </m:forEach>
          </tr>
        </m:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>