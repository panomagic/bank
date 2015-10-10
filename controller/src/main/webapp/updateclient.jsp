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
  <title><fmt:message key="updateclientpagetitle" /></title>
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
          <li><a href="viewaccounts"><fmt:message key="gotoaccountslistlink" /></a></li>
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
    <h2><fmt:message key="clientupdate" /></h2>
    <form name="updateclient" action="updatedeleteclient" method="POST">
      <div class="form-group"><b><fmt:message key="clientfullname" /></b><br>
        <input type="text" name="fullname" value="${client.fullName}" size="50">
      </div>

      <div class="form-group"><b><fmt:message key="gender" /></b><br>
        <c:choose>
          <c:when test="${client.gender == 'MALE'}">
            <input type="radio" name="gender" value="m" checked> <fmt:message key="male" /><br>
            <input type="radio" name="gender" value="f"> <fmt:message key="female" /><br>
          </c:when>
          <c:otherwise>
            <input type="radio" name="gender" value="m"> <fmt:message key="male" /><br>
            <input type="radio" name="gender" value="f" checked> <fmt:message key="female" /><br>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="form-group"><b><fmt:message key="dateofbirth" /></b><br>
        <input type="date" name="dateofbirth"
               value="<fmt:formatDate value="${client.dateOfBirth}" pattern="dd.MM.yyyy" />" required>
      </div>
      <div class="form-group"><b><fmt:message key="dateofreg" /></b><br>
        <input type="date" name="dateofreg"
               value="<fmt:formatDate value="${client.dateOfReg}" pattern="dd.MM.yyyy" />" required>
      </div>

      <p><input type="submit" class="btn btn-success" value="<fmt:message key="savebutton" />">
        <input type="reset" class="btn btn-default" value="<fmt:message key="cancelbutton" />"></p>
    </form>
  </div>
</body>
</html>