<%@ page import="bean.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Изменение клиента</title>
</head>
<body>
<h2>Изменение клиента</h2>
<form name="updateclient" method="POST">
  <% Client client = (Client) request.getAttribute("client"); %>
  <p><b>ФИО клиента:</b><br>
    <input type="text" name="fullname" value="<%= client.getFullName() %>" size="50">
  </p>
  <p><b>Пол:</b><br>
    <input type="radio" name="gender" value="m"> Мужской<br>
    <input type="radio" name="gender" value="f"> Женский<br>
  </p>
  <p><b>Дата рождения (MM/dd/yyyy):</b><br>
    <input type="date" name="dateofbirth" value="<%= client.getDateOfBirth() %>"></p>
  <p><b>Дата регистрации (MM/dd/yyyy):</b><br>
    <input type="date" name="dateofreg" value="<%= client.getDateOfReg() %>"></p>
  <p><input type="submit" value="Сохранить изменения">
    <input type="reset" value="Отменить ввод"></p>
</form>
<p><a href="viewclients">Назад к списку клиентов</a></p>
</body>
</html>