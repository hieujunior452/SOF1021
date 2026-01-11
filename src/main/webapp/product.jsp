<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết sản phẩm - Bee Phone</title>
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
    <c:if test="${phone != null}">
        <div class="product-detail">
            <div>
                <img src="images/${phone.image}" alt="${phone.phoneName}" onerror="this.src='images/no-image.jpg'">
            </div>
            <div class="product-info">
                <h2>${phone.phoneName}</h2>
                <c:if test="${brand != null}">
                    <p style="color: var(--text-light); margin-bottom: 1rem;"><strong>Thương hiệu:</strong> ${brand.brandName}</p>
                </c:if>
                <div class="price"><fmt:formatNumber value="${phone.price}" type="number" /> đ</div>
                <p style="margin: 1rem 0;"><strong>Tồn kho:</strong> <span style="color: ${phone.stock > 0 ? 'var(--success-color)' : 'var(--danger-color)'}">${phone.stock} sản phẩm</span></p>
                <div class="description">
                    <h3 style="margin-bottom: 0.5rem;">Mô tả sản phẩm</h3>
                    <p>${phone.description != null ? phone.description : 'Chưa có mô tả cho sản phẩm này.'}</p>
                </div>
                
                <c:if test="${sessionScope.user != null}">
                    <c:if test="${phone.stock > 0}">
                        <form action="/cart" method="post" style="margin-top: 2rem;">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="phoneId" value="${phone.phoneId}">
                            <div class="form-group">
                                <label>Số lượng:</label>
                                <input type="number" name="quantity" value="1" min="1" max="${phone.stock}" class="quantity-input" required>
                            </div>
                            <button type="submit" class="btn">Thêm vào giỏ hàng</button>
                        </form>
                    </c:if>
                    <c:if test="${phone.stock <= 0}">
                        <div style="margin-top: 2rem; padding: 1rem; background: #f8d7da; color: #721c24; border-radius: 10px; border-left: 4px solid #dc3545;">
                            <strong>Sản phẩm đã hết hàng!</strong>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <div style="margin-top: 2rem; padding: 1rem; background: #fff3cd; color: #856404; border-radius: 10px; border-left: 4px solid #ffc107;">
                        <strong>Vui lòng <a href="/login" style="color: var(--primary-color); text-decoration: none; font-weight: 600;">đăng nhập</a> để mua hàng</strong>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>
    
    <c:if test="${phone == null}">
        <div style="text-align: center; padding: 3rem;">
            <h2>Sản phẩm không tồn tại</h2>
            <p style="margin: 1rem 0; color: var(--text-light);">Sản phẩm bạn đang tìm kiếm không có trong hệ thống.</p>
            <a href="/home" class="btn" style="margin-top: 1rem;">Quay về trang chủ</a>
        </div>
    </c:if>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
