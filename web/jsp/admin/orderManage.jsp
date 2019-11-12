<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/12
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Manage Platform</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div id="welcome" class="card-title"><b>Order Management</b></div>
<%@include file="adminNavi.jsp"%>
<div id="product" class="container">
    <div class="table">
        <table width="100%">
            <tr>
                <th width="15%"><b>Order ID</b></th>
                <th width="25%"><b>Address</b></th>
                <th width="15"><b>Order Time</b></th>
                <th width="15%"><b>Update Time</b></th>
                <th width="15%"><b>Status</b></th>
                <th width="15%"><b></b></th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.address}</td>
                    <td>${order.orderTime}</td>
                    <td>${order.updateTime}</td>
                    <td>${order.status}</td>
                    <td>
                        <a
                                href="frontservlet?command=AdminManageOrder&method=view&order=${order.orderId}"
                           methods="post" role="button">View</a></td>
                    <td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>

