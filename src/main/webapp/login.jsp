<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>

<h2>Đăng nhập</h2>

<form action="/login" method="post">
    <label>Tài khoản:</label>
    <input type="text" name="username" placeholder="Tài khoản" required><br><br>

    <label>Mật khẩu:</label>
    <input type="password" name="password" placeholder="Mật khẩu" required><br><br>

    <button type="submit">Đăng nhập</button>
    <button type="button"><a href="/register.jsp">Đăng ký</a></button>
</form>

<p style="color:red">${error}</p>

</body>
</html>
