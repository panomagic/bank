<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><fmt:message key="addtransresultpagetitle" /></title>
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
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><fmt:message key="addmodalheader" /></h4>
        </div>
        <div class="modal-body">
          <p><b><fmt:message key="transactionsuccess" /></b></p>
        </div>
        <div class="modal-footer">
          <button id="okButton" type="button" class="btn btn-success" data-dismiss="modal">OK</button>
        </div>
      </div>

    </div>
  </div>

  <script type="text/javascript">
    $(window).load(function(){
      $('#myModal').modal({backdrop: "static"});
    });
  </script>

  <c:choose>
    <c:when test="${sessionScope.LOGGED_USER.role == 'ADMINISTRATOR'}">
      <script type="text/javascript">
        document.getElementById("okButton").onclick = function () {
          location.href = "/viewaccounts";
        };
      </script>
    </c:when>
    <c:when test="${sessionScope.LOGGED_USER.role == 'CLIENT'}">
      <script type="text/javascript">
        document.getElementById("okButton").onclick = function () {
          location.href = "/clientinfo";
        };
      </script>
    </c:when>
  </c:choose>
</body>
</html>