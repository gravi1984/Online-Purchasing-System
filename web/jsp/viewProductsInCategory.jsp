<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/14
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>York Way Coffee</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div id="welcome" class="card-title"><b>Products</b></div>
<%@include file="user/userNavi.jsp"%>
<div class="container">
    <div id="product" class="table">
        <table width="100%" align="left">
            <tr>
                <th width="20%"><b>Product Name</b></th>
                <th width="30%"><b>Info</b></th>
                <th width="10"><b>Price</b></th>
                <th width="10%"><b>Weight</b></th>
                <th width="30%"><b>Amount</b></th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.productName}</td>
                    <td>${product.info}</td>
                    <td>${product.price}</td>
                    <td>${product.weight}</td>
                    <td>
                        <form
                                action="frontservlet?command=AddToCart&product=${product.productId}&category=${category.categoryId}"
                                method="post">
                            <input id="name" type="text" name="amount" value="1">
                            <input type="submit" value="Add to Cart">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>