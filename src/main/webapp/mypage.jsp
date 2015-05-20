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
  <h1>
    <%
      String name = request.getParameter("name");
    %>
    Hello <%= name%> from JSP TestServlet</h1>
</body>
</html>
