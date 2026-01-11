<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán - Bee Phone</title>
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
        <li><a href="/cart">Giỏ hàng</a></li>
        <li><a href="/order">Đơn hàng</a></li>
        <c:if test="${sessionScope.userFullName != null}">
            <li class="user-greeting">Xin chào, ${sessionScope.userFullName}</li>
        </c:if>
        <c:if test="${sessionScope.userFullName == null}">
            <li class="user-greeting">Xin chào, ${sessionScope.user}</li>
        </c:if>
        <li><a href="/logout">Đăng xuất</a></li>
    </ul>
</nav>

<div class="container">
    <h2 style="margin-bottom: 1rem;">Thanh toán đơn hàng</h2>
    
    <c:if test="${error != null}">
        <div class="alert alert-error">
            ${error}
        </div>
    </c:if>
    
    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-bottom: 2rem;" class="checkout-grid">
        <!-- Thông tin khách hàng -->
        <div style="background: white; padding: 1.5rem; border-radius: 16px; box-shadow: var(--shadow-md);">
            <h3 style="margin-bottom: 1rem; color: var(--primary-color);">Thông tin khách hàng</h3>
            <c:if test="${user != null}">
                <div style="margin-bottom: 1rem;">
                    <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;"><strong>Họ và tên:</strong></p>
                    <p style="font-size: 1.05rem; font-weight: 600;">${user.fullName != null ? user.fullName : 'Chưa cập nhật'}</p>
                </div>
                <div style="margin-bottom: 1rem;">
                    <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;"><strong>Số điện thoại:</strong></p>
                    <p style="font-size: 1.05rem; font-weight: 600;">${user.phone != null ? user.phone : 'Chưa cập nhật'}</p>
                </div>
                <div style="margin-bottom: 1rem;">
                    <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;"><strong>Email:</strong></p>
                    <p style="font-size: 1.05rem; font-weight: 600;">${user.email != null ? user.email : 'Chưa cập nhật'}</p>
                </div>
            </c:if>
        </div>
        
        <!-- Form đặt hàng -->
        <div style="background: white; padding: 1.5rem; border-radius: 16px; box-shadow: var(--shadow-md);">
            <h3 style="margin-bottom: 1rem; color: var(--primary-color);">Địa chỉ nhận hàng</h3>
            <form action="/order" method="post">
                <input type="hidden" name="action" value="checkout">
                
                <div class="form-group">
                    <label>Địa chỉ nhận hàng: <span style="color: red;">*</span></label>
                    <textarea name="address" placeholder="Nhập địa chỉ nhận hàng (số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố)" required style="min-height: 100px;"></textarea>
                </div>
                
                <div style="background: var(--bg-light); padding: 1rem; border-radius: 10px; margin: 1.5rem 0;">
                    <h4 style="margin-bottom: 0.75rem;">Tóm tắt đơn hàng</h4>
                    <c:if test="${cartItems != null && !empty cartItems}">
                        <c:forEach var="item" items="${cartItems}">
                            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem; padding-bottom: 0.5rem; border-bottom: 1px solid var(--border-color);">
                                <span>${item.phone.phoneName} x ${item.cart.quantity}</span>
                                <strong><fmt:formatNumber value="${item.subtotal}" type="number" /> đ</strong>
                            </div>
                        </c:forEach>
                    </c:if>
                    <div style="display: flex; justify-content: space-between; margin-top: 1rem; padding-top: 1rem; border-top: 2px solid var(--primary-color); font-size: 1.25rem;">
                        <strong>Tổng tiền:</strong>
                        <strong style="color: var(--primary-color);"><fmt:formatNumber value="${total}" type="number" /> đ</strong>
                    </div>
                </div>
                
                <button type="submit" class="btn btn-success" style="width: 100%; padding: 1rem; font-size: 1.2rem;">Xác nhận đặt hàng</button>
            </form>
            <div style="margin-top: 1rem; text-align: center;">
                <a href="/cart" class="btn" style="width: 100%; display: inline-block; text-align: center; text-decoration: none;">Quay lại giỏ hàng</a>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
