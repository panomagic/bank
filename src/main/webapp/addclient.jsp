<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Ввод нового клиента</title>
</head>
<body>
<h2>Добавление нового клиента</h2>
<form name="addclient" method="POST">
  <p><b>ФИО клиента:</b><br>
    <input type="text" name="fullname" placeholder="ФИО" size="50">
  </p>
  <p><b>Пол:</b><br>
    <input type="radio" name="gender" value="m"> Мужской<br>
    <input type="radio" name="gender" value="f"> Женский<br>
  </p>
  <p><b>Дата рождения (dd.MM.yyyy):</b><br>
    <input type="date" name="dateofbirth"></p>
  <p><b>Дата регистрации (dd.MM.yyyy):</b><br>
    <input type="date" name="dateofreg"></p>
  <p><input type="submit" value="Сохранить">
    <input type="reset" value="Очистить"></p>
</form>
<p><a href="viewclients">Назад к списку клиентов</a></p>
</body>
</html>