<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="utf-8">
  <title>Bank web-application | Веб-приложение Банк</title>
</head>
<body>
  <h2>Enter your login and password | Введите ваш логин и пароль</h2>
  <form action="login" method="POST">
    <p><b>Username / Имя пользователя:</b><br>
      <input type="text" name="userName" size="35">
    </p>
    <p><b>Password / Пароль:</b><br>
      <input type="text" name="password" size="35">
    </p>
    <p><b>Choose language / Выберите язык:</b><br>
      <select name="chosenlanguage">
        <option value="en">English</option>
        <option value="ru">Русский</option>
      </select>
    </p>

    <p>
      <input type="submit" value="Log in | Вход" style="width: 150px">
      <input type="reset" value="Clear | Очистить">
    </p>
  </form>
</body>
</html>
