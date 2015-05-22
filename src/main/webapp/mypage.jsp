<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 16.05.2015
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TESTSERVLET</title>
</head>
<body>
  <br>
    <%
      String name = (String) request.getAttribute("name");
      double num1 = Math.random();
    %>
    Hello <%= name%> from JSP TestServlet<br>
    Наше случайное число равно <%= num1 %>
  </h1>
</body>
</html>
