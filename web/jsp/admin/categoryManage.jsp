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
<div id="welcome" class="card-title"><b>Category Management</b></div>
<%@include file="adminNavi.jsp"%>
<div id="category" class="container">
    <div class="table">
        <table width="100%">
            <tr>
                <th width="55%"><b>Name</b></th>
                <th width="15%"><b> </b></th>
                <th width="15%"><b> </b></th>
                <th width="15%"><b> </b></th>
            </tr>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.categoryName}</td>
                    <td>
                        <a href="frontservlet?command=AdminManageCategory&method=edit&category=${category.categoryId}"
                           methods="post" role="button">Edit</a></td>
                    <td>
                        <a
                                href="frontservlet?command=AdminManageCategory&method=view&category=${category.categoryId}"
                                methods="post" role="button">View</a></td>
                    <td>
                        <a href="frontservlet?command=AdminManageCategory&method=delete&category=${category.categoryId}"
                           methods="post" role="button">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="float-right">
        <a href="frontservlet?command=AdminManageCategory&method=create"
           methods="post" role="button"><b>Add new Category</b></a>
    </div>

</div>
</body>
</html>
