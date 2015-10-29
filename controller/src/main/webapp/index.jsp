<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Bank Web App | Веб-приложение Банк</title>

  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <!-- Optional theme -->
  <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <!-- Latest compiled JavaScript -->
  <script src="../js/bootstrap.min.js"></script>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->
</head>

<body>

<div class="container">

  <div class="jumbotron" style="margin-top: 20px;">
    <h2>Bank | Банк</h2>
    <sec:authorize access="!isAuthenticated()">
      <p class="lead">
        Welcome to Bank Web App! | Добро пожаловать в Веб-приложение "Банк"!
      </p>
      <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
      <p class="lead">
        You logged in successfully | Вход выполнен удачно
      </p>
      <p>Your login | Ваш логин: <sec:authentication property="principal.username" /><br>
      Your role | Ваша роль: <sec:authentication property="principal.authorities" /></p>
      <form action="/language" method="POST" role="form">
        <b>Choose language to proceed | Выберите язык для продолжения:</b>
        <div class="radio">
          <label><input type="radio" name="chosenlanguage" value="en" checked>English</label>
        </div>
        <div class="radio">
          <label><input type="radio" name="chosenlanguage" value="ru">Русский</label>
        </div>
        <button type="submit" class="btn btn-lg btn-success">  OK  </button>
      </form>
      <br>
      <p><a class="btn btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>
    </sec:authorize>
  </div>
  <div class="footer">
    <p align="center">© 2015</p>
  </div>

</div>
</body>
</html>