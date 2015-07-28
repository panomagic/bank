<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Изменение клиента</title>
</head>
<body>
<h2>Изменение клиента</h2>
<form name="updateclient" action="updatedeleteclient" method="POST">
  <p><b>ФИО клиента:</b><br>
    <input type="text" name="fullname" value="${client.fullName}" size="50">
  </p>
  <p><b>Пол:</b><br>
    <c:choose>
      <c:when test="${client.gender == 'MALE'}">
        <input type="radio" name="gender" value="m" checked> Мужской<br>
        <input type="radio" name="gender" value="f"> Женский<br>
      </c:when>
      <c:otherwise>
        <input type="radio" name="gender" value="m"> Мужской<br>
        <input type="radio" name="gender" value="f" checked> Женский<br>
      </c:otherwise>
    </c:choose>
  </p>
  <p><b>Дата рождения (dd.MM.yyyy):</b><br>
    <input type="date" name="dateofbirth"
           value="<fmt:formatDate value="${client.dateOfBirth}" pattern="dd.MM.yyyy" />"></p>
  <p><b>Дата регистрации (dd.MM.yyyy):</b><br>
    <input type="date" name="dateofreg"
           value="<fmt:formatDate value="${client.dateOfReg}" pattern="dd.MM.yyyy" />"></p>
  <p><input type="submit" value="Сохранить изменения">
    <input type="reset" value="Отменить ввод"></p>
</form>
<p><a href="viewclients">Назад к списку клиентов</a></p>
</body>
</html>