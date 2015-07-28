<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="utf-8">
  <title>Ошибка</title>
</head>
<body>
  <p><b>Ошибка входа! Введите логин и пароль</b></p>
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