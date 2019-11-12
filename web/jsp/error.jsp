<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/14
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
There must be some errors: ${errMsg}...
<button type="button" style="text-align: right; margin:0 10%;"
        class="btn btn-success" onclick="history.go(-1);">Return</button>
</body>
</html>
