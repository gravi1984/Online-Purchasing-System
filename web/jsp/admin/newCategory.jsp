<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/12
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="welcome" class="card-title"><b>Add Category</b></div>
<div class="container">
    <form action="frontservlet?command=AdminAddCategory" method="POST">
        <div class="form-group row">
            <label for="inputCategoryName" class="col-sm-2 col-form-label">Category Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputCategoryName" name="categoryName">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Confirm</button>
        <button type="button" class="btn btn-light" onclick="history.go(-1);">Cancel</button>
    </form>
</div>

</body>
</html>

