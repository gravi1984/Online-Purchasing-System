<%@ page import="security.AppSession" %>
<%@ page import="domain.Customer" %><%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/27
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Confirm Order</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<shiro:authenticated>
    <shiro:hasRole name="customer">
        <div id="welcome" class="card-title"><b>Please Confirm Your Shipping Address</b></div>
        <div class="container">
            <form action="frontservlet?command=PlaceOrder" method="POST">
                <% Customer customer = AppSession.getUser();%>
                        <div
                    class="form-group row">
                    <label for="inputAddress" class="col-sm-2 col-form-label">Address</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputAddress" name="address"
                               value="<%=customer.getAddress()%>">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Confirm</button>
                <button type="button" class="btn btn-light" onclick="history.go(-1);">Cancel
                </button>
            </form>
        </div>
    </shiro:hasRole>
</shiro:authenticated>
</body>
</html>
