<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/10/1
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="security.AppSession" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>York Way Coffee</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<shiro:notAuthenticated>
    <div id="welcome" class="card-title"><b>Login</b></div>
    <div class="container">
        <form action="frontservlet?command=Login" method="post">
            <div class="form-group row">
                <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputUsername" name="username">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword"
                           name="password">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
            <button type="button" class="btn btn-light" onclick="history.go(-1);">Cancel</button>
            <br>
        </form>
        <a methods="post" role="button" href="frontservlet?command=ForwardRegister">Sign up</a>
    </div>
</shiro:notAuthenticated>
<shiro:authenticated>
    You are already logged in as <%=AppSession.getUser().getUsername()%>
    <div class="float-left">
        <button type="button" class="btn btna-light" onclick="history.go(-1);">Back</button>
    </div>
</shiro:authenticated>
</body>
</html>
