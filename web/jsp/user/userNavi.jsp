<%--
  Created by IntelliJ IDEA.
  User: DennyLee
  Date: 2019/10/2
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="security.AppSession" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="container">
    <div class="float-right">
        <shiro:authenticated>
            <b>Hello, <%=AppSession.getUser().getUsername()%> <a href="frontservlet?command=Logout">
                Logout</a></b>
        </shiro:authenticated>
        <shiro:notAuthenticated>
            <b><a href="frontservlet?command=ForwardUserLogin">Log In</a></b>
        </shiro:notAuthenticated>
    </div>
</div>
<br>
<shiro:authenticated>
    <div id="shop">
        <ul>
            <li><a href="frontservlet?command=ForwardUserHome">Home</a></li>
            <li><a href="frontservlet?command=ViewProducts">All Products</a></li>
            <li><a href="frontservlet?command=ViewCategory">Roast</a></li>
            <li><a href="frontservlet?command=ViewCart">Cart</a></li>
            <li><a href="frontservlet?command=ViewOrder">My Order</a></li>
            <li>
                <div id="search1">
                    <form action="frontservlet?command=SearchProduct" method="post">
                        <input type="text" name="name">
                        <input type="submit" value="Search">
                    </form>
                </div>
            </li>
        </ul>
    </div>
</shiro:authenticated>

<shiro:notAuthenticated>
    <div id="shop">
        <ul>
            <li><a href="frontservlet?command=ViewProducts">All Products</a></li>
            <li><a href="frontservlet?command=ViewCategory">Roast</a></li>
            <li>
                <div id = "search2">
                    <form action="frontservlet?command=SearchProduct" method="post">
                        <input type="text" name="name">
                        <input type="submit" value="Search">
                    </form>
                </div>
            </li>
        </ul>
    </div>
</shiro:notAuthenticated>