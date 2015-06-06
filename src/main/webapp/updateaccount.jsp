<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>Изменение счета</title>
</head>
<body>
  <h2>Изменение счета</h2>
  <form name="updateaccount" action="updatedeleteaccount" method="POST">
    <p><b>Выберите клиента:</b><br>
      <select name="chooseclient">
        <c:forEach var="client" items="${allClients}">
          <option value="${client.clientID}"
            <c:choose>
              <c:when test="${client.clientID == account.clientID}">
                selected="selected">
              </c:when>
              <c:otherwise>
                >
              </c:otherwise>
            </c:choose>
            ${client.fullName}
          </option>
        </c:forEach>
      </select>
    </p>

    <p><b>Тип счета:</b><br>
      <c:choose>
        <c:when test="${account.accTypeID == 1}">
          <input type="radio" name="acctypeID" value="1" checked> DEBIT<br>
          <input type="radio" name="acctypeID" value="2"> CREDIT<br>
        </c:when>
        <c:when test="${account.accTypeID == 2}">
          <input type="radio" name="acctypeID" value="1"> DEBIT<br>
          <input type="radio" name="acctypeID" value="2" checked> CREDIT<br>
        </c:when>
      </c:choose>
    </p>

    <p><b>Валюта:</b><br>
      <c:choose>
        <c:when test="${account.currencyID == 1}">
          <input type="radio" name="currencyID" value="1" checked> UAH<br>
          <input type="radio" name="currencyID" value="2"> USD<br>
          <input type="radio" name="currencyID" value="3"> EUR<br>
        </c:when>
        <c:when test="${account.currencyID == 2}">
          <input type="radio" name="currencyID" value="1"> UAH<br>
          <input type="radio" name="currencyID" value="2" checked> USD<br>
          <input type="radio" name="currencyID" value="3"> EUR<br>
        </c:when>
        <c:when test="${account.currencyID == 3}">
          <input type="radio" name="currencyID" value="1"> UAH<br>
          <input type="radio" name="currencyID" value="2"> USD<br>
          <input type="radio" name="currencyID" value="3" checked> EUR<br>
        </c:when>
      </c:choose>
    </p>

    <p><input type="submit" value="Сохранить изменения">
      <input type="reset" value="Отменить ввод"></p>
  </form>
  <p><a href="viewaccounts">Назад к списку счетов</a></p>
</body>
</html>