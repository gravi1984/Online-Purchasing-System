<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/12
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Manage Platform</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">

</head>
<body>
<div id="welcome">Manage Platform</div>
<%@include file="adminNavi.jsp" %>
<shiro:hasRole name="manager">
    <div class="container">
        <a role="button" methods="POST"
           href="frontservlet?command=ForwardAddAdmin"><b>Add Staff</b></a>
    </div>
</shiro:hasRole>
</body>
</html>
