<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="utf-8">
  <title>Выход из системы</title>
</head>
<body>
  <h2>Вы вышли из системы</h2>
  <p><b>Чтобы войти снова, введите логин и пароль:</b></p>
<form action="login" method="POST">
  <p><b>Имя пользователя:</b><br>
    <input type="text" name="userName" size="35">
  </p>
  <p><b>Пароль:</b><br>
    <input type="text" name="password" size="35">
  </p>
  <p>
    <input type="submit" value="Вход">
    <input type="reset" value="Очистить">
  </p>
</form>
</body>
</html>