<%--
  Created by IntelliJ IDEA.
  User: AzRy
  Date: 30-May-19
  Time: 03:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Get Json from your Name!</title>
</head>
<body>
    <h1>Server is up and running!</h1>
    <form action="/service" method="get">
        <label>First Name</label> <input type="text" name="firstName"> <br>
        <label>Last Name</label>  <input type="text" name="lastName"> <br>
        <input type="submit" name="Gachite!">
    </form>
</body>
</html>
