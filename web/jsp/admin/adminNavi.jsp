<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/10/7
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<shiro:authenticated>
    <shiro:hasAnyRoles name="manager,clerk">
        <div id="navi">
            <ul>
                <li><a href="frontservlet?command=AdminProduct">Product Management</a></li>
                <li><a href="frontservlet?command=AdminCategory">Category Management</a></li>
                <li><a href="frontservlet?command=AdminOrder">OrderManagement</a></li>
                <li><a href="frontservlet?command=Logout">Logout</a> </li>
            </ul>
        </div>
    </shiro:hasAnyRoles>
</shiro:authenticated>
<shiro:notAuthenticated>
    <script>
        alert("Please log in");
        window.location.href = "frontservlet?command=ForwardAdminLogin"
    </script>
</shiro:notAuthenticated>
