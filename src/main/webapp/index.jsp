<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Bee Phone</title>
</head>

<body>

<header>
    <h1>Bee Phone</h1>
</header>
    <nav>
        <ul>
            <li><a href="/home">Trang chủ</a></li>
            <li><a href="/sanpham">Sản phẩm</a></li>
            <li><a href="#">Giới thiệu</a></li>
            <li><a href="#">Liên hệ</a></li>
            <c:if test="${sessionScope.user != null}">
                Xin chào ${sessionScope.user}
            </c:if>

            <c:if test="${sessionScope.user == null}">
            <li><a href="/login">Đăng nhập</a></li>
            </c:if>

        </ul>
    </nav>

    <section class="banner">
        <img src="banner.jpg" width="100%">
    </section>

    <c:forEach items="${listPhone}" var="p">
        <div class="product">
            <img src="images/${p.image}">
            <h3>${p.phoneName}</h3>
            <p>${p.price} đ</p>
            <button type="submit" name="action" value="giohang">Thêm vào giỏ hàng</button>
        </div>
    </c:forEach>

<footer>
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>