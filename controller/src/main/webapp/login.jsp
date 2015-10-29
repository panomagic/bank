<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bank web-application | Веб-приложение Банк</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <!-- Latest compiled JavaScript -->
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->

  <style>
    .modal-header, h4 {
      background-color: #5cb85c;
      color:white !important;
      text-align: center;
      font-size: 30px;
    }
  </style>
</head>
<body>
  <div class="container">
    <!-- Modal -->
    <div class="modal fade" id="LoginModal" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header" style="padding:35px 50px;">
            <h4><span class="glyphicon glyphicon-lock"></span> Login / Вход</h4>
          </div>
          <div class="modal-body" style="padding:40px 50px;">
            <c:url value="/j_spring_security_check" var="loginUrl" />
            <form action="${loginUrl}" method="POST" role="form">
              <div class="form-group">
                <label for="usrname"><span class="glyphicon glyphicon-user"></span> Username / Имя пользователя</label>
                <input type="text" class="form-control" name="j_username" id="usrname" placeholder="Enter your username" required autofocus>
              </div>
              <div class="form-group">
                <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> Password / Пароль</label>
                <input type="text" class="form-control" name="j_password" id="psw" placeholder="Enter password">
              </div>
              <div class="checkbox">
                <label><input type="checkbox" value="" checked>Remember me</label>
              </div>
              <br>
              <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span> Login / Вход</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript">
    $(window).load(function(){
      $('#LoginModal').modal({backdrop: "static"});
    });
  </script>
</body>
</html>