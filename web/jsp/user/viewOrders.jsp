<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/27
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Orders</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<shiro:authenticated>
    <div id="welcome" class="card-title"><b>My Order</b></div>
    <shiro:hasRole name="customer">
        <%@include file="userNavi.jsp"%>
        <div class="container">
            <div id="product" class="table">
                <table width="100%" align="center">
                    <tr>
                        <th width="15%" align="center"><b>Order ID</b></th>
                        <th width="40%" align="center"><b>Total Price</b></th>
                        <th width="15" align="center"><b>Purchase Time</b></th>
                        <th width="15%" align="center"><b>Status</b></th>
                        <th><b></b></th>
                    </tr>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.totalPrice}</td>
                            <td>${order.orderTime}</td>
                            <td>${order.status}</td>
                            <td><a
                                    href="frontservlet?command=ViewOrderDetail&order=${order.orderId}"
                                    methods="post" role="button">View</a></td>
                            </td></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </shiro:hasRole>
</shiro:authenticated>
<shiro:notAuthenticated>
    <script>
        alert("Please log in");
        window.location.href = "frontservlet?command=UserLogin"
    </script>
</shiro:notAuthenticated>
</body>
</html>

