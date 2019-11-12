<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/10/1
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>York Way Coffee</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>

<div id="welcome" class="card-title"><b>Add Clerk</b></div>
<div class="container">
    <form action="frontservlet?command=AdminAddStaff" method="POST">
        <div class="form-group row">
            <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputUsername" name="username">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="inputPassword" name="password">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputFirstName" class="col-sm-2 col-form-label">First Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputFirstName" name="firstName">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputLastName" class="col-sm-2 col-form-label">Last Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputLastName" name="lastName">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputStartDate" class="col-sm-2 col-form-label">Start Date</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputStartDate"
                       name="startDate" placeholder="yyyy-mm-dd">
            </div>
        </div>

        <div class="form-group row">
            <label for="inputEndDate" class="col-sm-2 col-form-label">End Date</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputEndDate"
                       name="endDate" placeholder="yyyy-mm-dd">
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Confirm</button>
        <button type="button" class="btn btn-light" onclick="history.go(-1);">Cancel</button>
    </form>


</div>
</body>
</html>
