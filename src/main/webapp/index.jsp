<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bee Phone - Cửa hàng điện thoại</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Bee Phone</h1>
</header>

<nav>
    <input type="checkbox" id="menu-toggle" class="menu-toggle">
    <label for="menu-toggle" class="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </label>
    <ul class="menu">
        <li><a href="/home">Trang chủ</a></li>
        <li><a href="/home">Sản phẩm</a></li>
        <c:if test="${sessionScope.user != null}">
            <li><a href="/cart">Giỏ hàng</a></li>
            <li><a href="/order">Đơn hàng</a></li>
            <li class="user-greeting">
                <c:if test="${sessionScope.userFullName != null}">
                    Xin chào, ${sessionScope.userFullName}
                </c:if>
                <c:if test="${sessionScope.userFullName == null}">
                    Xin chào, ${sessionScope.user}
                </c:if>
            </li>
            <li><a href="/logout">Đăng xuất</a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li><a href="/login">Đăng nhập</a></li>
            <li><a href="/register">Đăng ký</a></li>
        </c:if>
    </ul>
</nav>

<div class="container">
    <c:if test="${listBrands != null && !empty listBrands}">
        <div class="brand-filter">
            <form action="/home" method="get">
                <label><strong>Lọc theo thương hiệu:</strong></label>
                <select name="brandId" style="padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;">
                    <option value="">Tất cả</option>
                    <c:forEach var="brand" items="${listBrands}">
                        <option value="${brand.brandId}" <c:if test="${param.brandId == brand.brandId}">selected</c:if>>${brand.brandName}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn" style="padding: 0.5rem 1.5rem; margin-left: 1rem;">Lọc</button>
            </form>
        </div>
    </c:if>
    
    <h2 style="margin-bottom: 1rem;">Danh sách sản phẩm</h2>
    
    <c:if test="${listPhone != null && !empty listPhone}">
        <div class="product-grid">
            <c:forEach items="${listPhone}" var="p">
                <div class="product-card">
                    <a href="/product?id=${p.phoneId}" style="text-decoration: none; color: inherit;">
                        <img src="images/${p.image}" alt="${p.phoneName}">
                        <div class="product-card-content">
                            <h3>${p.phoneName}</h3>
                            <div class="price"><fmt:formatNumber value="${p.price}" type="number" /> đ</div>
                            <p style="color: #666; font-size: 0.9rem;">Tồn kho: ${p.stock}</p>
                        </div>
                    </a>
                    <div class="product-card-content" style="padding-top: 0;">
                        <a href="/product?id=${p.phoneId}" class="btn" style="width: 100%; text-align: center; display: block;">Xem chi tiết</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    
    <c:if test="${listPhone == null || empty listPhone}">
        <div class="empty-cart">
            <h2>Không có sản phẩm nào</h2>
            <p>Vui lòng quay lại sau!</p>
        </div>
    </c:if>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>