<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - Bee Phone</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Bee Phone</h1>
</header>

<nav>
    <ul>
        <li><a href="/home">Trang chủ</a></li>
        <li><a href="/home">Sản phẩm</a></li>
        <li><a href="/login">Đăng nhập</a></li>
        <li><a href="/register">Đăng ký</a></li>
    </ul>
</nav>

<div class="container">
    <div class="form-container">
        <h2 style="text-align: center; margin-bottom: 1.5rem;">Đăng ký tài khoản</h2>
        
        <c:if test="${error != null}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>
        
        <form action="/register" method="post">
            <div class="form-group">
                <label>Tài khoản:</label>
                <input type="text" name="username" placeholder="Nhập tài khoản" required>
            </div>
            
            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" name="password" placeholder="Nhập mật khẩu" required>
            </div>
            
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" placeholder="Nhập email" required>
            </div>
            
            <button type="submit" class="btn" style="width: 100%; margin-top: 1rem;">Đăng ký</button>
        </form>
        
        <div style="text-align: center; margin-top: 1.5rem;">
            <p>Đã có tài khoản? <a href="/login" style="color: #667eea; text-decoration: none;">Đăng nhập ngay</a></p>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
