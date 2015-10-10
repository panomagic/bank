<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Logout / Выход</title>
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
  <div class="container">
    <div class="alert alert-warning">
      <h2>You logged out / Вы вышли из системы</h2>
      <p><b>To log in again please click the button below / Чтобы войти снова, нажмите кнопку ниже</b></p>
    </div>
    <button type="submit" class="btn btn-success" id="loginButton">Login / Вход</button>
  </div>

  <script type="text/javascript">
    document.getElementById("loginButton").onclick = function () {
      location.href = "/login";
    };
  </script>
</body>
</html>