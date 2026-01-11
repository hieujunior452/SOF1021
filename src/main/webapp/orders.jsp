<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn hàng của tôi - Bee Phone</title>
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
    <h2 style="margin-bottom: 1rem;">Đơn hàng của tôi</h2>
    
    <c:if test="${param.success != null}">
        <div class="alert alert-success">
            ✅ Đặt hàng thành công! Cảm ơn bạn đã mua sắm tại Bee Phone.
        </div>
    </c:if>
    
    <c:if test="${orders != null && !empty orders}">
        <div style="overflow-x: auto;">
            <table>
                <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Ngày đặt</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td data-label="Mã đơn hàng"><strong>#${order.orderId}</strong></td>
                            <td data-label="Ngày đặt">
                                <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td data-label="Tổng tiền"><strong><fmt:formatNumber value="${order.total}" type="number" /> đ</strong></td>
                            <td data-label="Trạng thái">
                                <span style="padding: 0.25rem 0.75rem; border-radius: 20px; background: ${order.status == 'Đã đặt hàng' ? '#d4edda' : '#fff3cd'}; color: ${order.status == 'Đã đặt hàng' ? '#155724' : '#856404'}; font-size: 0.875rem; font-weight: 600;">
                                    ${order.status}
                                </span>
                            </td>
                            <td data-label="Thao tác">
                                <a href="/orderDetail?id=${order.orderId}" class="btn" style="padding: 0.5rem 1rem; font-size: 0.9rem; text-decoration: none; display: inline-block; text-align: center;">Chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    
    <c:if test="${orders == null || empty orders}">
        <div class="empty-cart">
            <h2>Bạn chưa có đơn hàng nào</h2>
            <p>Hãy mua sắm và đặt hàng ngay!</p>
            <a href="/home" class="btn" style="margin-top: 1rem;">Tiếp tục mua sắm</a>
        </div>
    </c:if>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
