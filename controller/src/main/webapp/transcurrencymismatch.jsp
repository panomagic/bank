<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="TranscurrmismatchBundle" />

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="pagetitle" /></title>
</head>
  <body>
    <h2><fmt:message key="currmismatcherror" /></h2>
    <p>
      <a href="addtransaction"><fmt:message key="tryagainlink" /></a><br>
    </p>
  </body>
</html>