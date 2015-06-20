<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Ввод нового счета</title>
</head>
<body>
<h2>Добавление нового счета</h2>
  <form name="addaccount" method="POST">
    <p><b>Выберите клиента:</b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.clientID}">${client.fullName}</option>
        </c:forEach>
      </select>
    </p>

    <p><b>Тип счета:</b><br>
      <input type="radio" name="acctypeID" value="1"> DEBIT<br>
      <input type="radio" name="acctypeID" value="2"> CREDIT<br>
    </p>

    <p><b>Валюта:</b><br>
      <input type="radio" name="currencyID" value="1"> UAH<br>
      <input type="radio" name="currencyID" value="2"> USD<br>
      <input type="radio" name="currencyID" value="3"> EUR<br>
    </p>

    <p><input type="submit" value="Сохранить">
      <input type="reset" value="Очистить"></p>
  </form>
  <p><a href="viewaccounts">Назад к списку счетов</a></p>
</body>
</html>