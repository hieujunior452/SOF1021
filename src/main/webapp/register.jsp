<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>

<h2>Đăng Ký</h2>

<form action="/register" method="post">
    <input name="username" placeholder="Tài khoản" required><br>
    <input type="password" name="password" placeholder="Mật khẩu" required><br>
    <input type="email" name="email" placeholder="Email" required><br>
    <button type="submit">Đăng ký</button>
</form>


<p style="color:red">${error}</p>

</body>
</html>
