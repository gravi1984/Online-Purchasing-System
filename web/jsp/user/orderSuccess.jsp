<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/9/27
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Congratulation!</title>
</head>
<body>
<shiro:authenticated>
    <shiro:hasRole name="customer">
        Thank you for your purchase!
        Your order is pending confirm.
        <div class="float-left">
            <a role="button" methods="post" href="frontservlet?command=ViewOrder">My
                Orders</a>
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
