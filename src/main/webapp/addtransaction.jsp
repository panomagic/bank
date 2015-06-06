<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
    <title>Создание транзакции</title>
</head>
<body>
  <h2>Произвести перевод денежных средств</h2>
  <form name="addtransaction" method="POST">
    <p><b>Выберите счет отправителя:</b><br>
      <select name="choosepayeraccount">
        <c:forEach var="account" items="${allAccounts}">
          <c:choose>
            <c:when test="${account.accTypeID == 1}">
              <c:set var="accType" value="DEBIT"/>
            </c:when>
            <c:when test="${account.accTypeID == 2}">
              <c:set var="accType" value="CREDIT"/>
            </c:when>
          </c:choose>
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${account.clientID == client.clientID}">
                <c:forEach var="currency" items="${allCurrencies}">
                  <c:choose>
                    <c:when test="${account.currencyID == currency.currencyID}">
                      <option value="${account.accountID}">
                        ${client.fullName} | ${accType} | ${account.balance} ${currency.currency}
                      </option>
                    </c:when>
                  </c:choose>
                </c:forEach>
              </c:when>
            </c:choose>
          </c:forEach>
        </c:forEach>
      </select>
    </p>

    <p><b>Выберите счет получателя:</b><br>
      <select name="chooserecipientaccount">
        <c:forEach var="account" items="${allAccounts}">
          <c:choose>
            <c:when test="${account.accTypeID == 1}">
              <c:set var="accType" value="DEBIT"/>
            </c:when>
            <c:when test="${account.accTypeID == 2}">
              <c:set var="accType" value="CREDIT"/>
            </c:when>
          </c:choose>
          <c:forEach var="client" items="${allClients}">
            <c:choose>
              <c:when test="${account.clientID == client.clientID}">
                <c:forEach var="currency" items="${allCurrencies}">
                  <c:choose>
                    <c:when test="${account.currencyID == currency.currencyID}">
                      <option value="${account.accountID}">
                          ${client.fullName} | ${accType} | ${account.balance} ${currency.currency}
                      </option>
                    </c:when>
                  </c:choose>
                </c:forEach>
              </c:when>
            </c:choose>
          </c:forEach>
        </c:forEach>
      </select>
    </p>

    <p><b>Введите сумму:</b><br>
      <input type="number" size="9" name="sum" min="0.01" value="0.00" step="0.01">
    </p>

    <p><input type="submit" value="Отправить">
      <input type="reset" value="Очистить"></p>
  </form>
  <p>
    <a href="viewaccounts">Вернуться к списку счетов</a><br>
    <a href="viewclients">Вернуться к списку клиентов</a>
  </p>
</body>
</html>