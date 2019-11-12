<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/7
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Manage Platform</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div id="welcome" class="card-title"><b>View Category</b></div>
    <%@include file="user/userNavi.jsp"%>
<div class="container">
    <div id="category" class="table">
        <table width="100%" align="left">
            <tr>
                <th width="80%"><b>Category Name</b></th>
                <th width="20%"><b> </b></th>
            </tr>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.categoryName}</td>
                    <td>
                        <a
                                href="frontservlet?command=ViewCategoryProduct&category=${category.categoryId}"
                                methods="post" role="button">View</a></td>
                </tr>
            </c:forEach>
        </table>
        <br>
    </div>
</div>

</body>
</html>
