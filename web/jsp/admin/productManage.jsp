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
<div id="welcome" class="card-title"><b>Product Management</b></div>
<%@include file="adminNavi.jsp"%>
<div id="product" class="container">
    <div class="table">
        <table width="100%">
            <tr>
                <th width="15%"><b>Product Name</b></th>
                <th width="25%"><b>Info</b></th>
                <th width="15"><b>Price</b></th>
                <th width="15%"><b>Weight</b></th>
                <th width="15%"><b>Inventory</b></th>
                <th width="15%"><b></b></th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.productName}</td>
                    <td>${product.info}</td>
                    <td>${product.price}</td>
                    <td>${product.weight}</td>
                    <td>${product.inventory}</td>
                    <td>
                        <a href="frontservlet?command=AdminManageProduct&method=edit&product=${product.productId}"
                           methods="post" role="button">Edit</a></td>
                    <td>
                        <a href="frontservlet?command=AdminManageProduct&method=delete&product=${product.productId}"
                           methods="post" role="button">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="float-right">
        <a href="frontservlet?command=AdminManageProduct&method=create"
           methods="post" role="button"><b>Add new product</b></a>
    </div>
</div>
</body>
</html>
