<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="m" uri="mytags" %>

<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ваши счета</title>
</head>
<body>
<h2>
  Вы вошли в систему как клиент
</h2>
<h2>
  Список ваших счетов
</h2>
  <m:clientInfo></m:clientInfo>
<p>
  <a href="addtransaction">Сделать перевод денег</a>
</p>
<p>
  <a href="transactionshistorybyclient">Просмотреть историю транзакций</a>
</p>
<p>
  <a href="logout">Выйти из системы</a>
</p>
</body>
</html>