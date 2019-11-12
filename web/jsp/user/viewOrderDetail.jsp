<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/28
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Orders</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>

<div id="welcome" class="card-title"><b>Products</b></div>
<%@include file="userNavi.jsp" %>
<shiro:authenticated>
    <shiro:hasRole name="customer">
        <div class="container">
            <div id="product" class="table">
                <table width="100%" align="center">
                    <tr>
                        <th width="20%" align="center"><b>Item</b></th>
                        <th width="20%" align="center"><b>Name</b></th>
                        <th width="20" align="center"><b>Roast</b></th>
                        <th width="20%" align="center"><b>Price</b></th>
                        <th width="20%" align="center"><b>Quantity</b></th>
                    </tr>
                    <c:forEach var="orderDetail" items="${orderDetails}" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${orderDetail.product.productName}</td>
                            <td>${orderDetail.productCategory.categoryName}</td>
                            <td>${orderDetail.product.price}</td>
                            <td>${orderDetail.productAmount}</td>
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

