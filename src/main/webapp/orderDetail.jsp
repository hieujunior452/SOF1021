<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng - Bee Phone</title>
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
    <h2 style="margin-bottom: 1rem;">Chi tiết đơn hàng #${order.orderId}</h2>
    
    <div style="background: white; padding: 1.5rem; border-radius: 16px; margin-bottom: 2rem; box-shadow: var(--shadow-md);">
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
            <div>
                <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;">Ngày đặt hàng</p>
                <p style="font-weight: 600; font-size: 1.05rem;"><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></p>
            </div>
            <div>
                <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;">Trạng thái</p>
                <p style="font-weight: 600;">
                    <span style="padding: 0.25rem 0.75rem; border-radius: 20px; background: ${order.status == 'Đã đặt hàng' ? '#d4edda' : '#fff3cd'}; color: ${order.status == 'Đã đặt hàng' ? '#155724' : '#856404'}; font-size: 0.875rem;">
                        ${order.status}
                    </span>
                </p>
            </div>
            <div>
                <p style="color: var(--text-light); margin-bottom: 0.25rem; font-size: 0.875rem;">Tổng tiền</p>
                <p style="font-weight: 700; font-size: 1.25rem; color: var(--primary-color);"><fmt:formatNumber value="${order.total}" type="number" /> đ</p>
            </div>
        </div>
        <c:if test="${order.address != null && !empty order.address}">
            <div style="margin-top: 1.5rem; padding-top: 1.5rem; border-top: 1px solid var(--border-color);">
                <p style="color: var(--text-light); margin-bottom: 0.5rem; font-size: 0.875rem;"><strong>Địa chỉ nhận hàng:</strong></p>
                <p style="font-weight: 600; font-size: 1.05rem; color: var(--text-dark);">${order.address}</p>
            </div>
        </c:if>
    </div>
    
    <h3 style="margin-bottom: 1rem;">Sản phẩm trong đơn hàng</h3>
    
    <c:if test="${items != null && !empty items}">
        <div style="overflow-x: auto;">
            <table>
                <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td data-label="Hình ảnh">
                                <c:if test="${item.phone != null}">
                                    <img src="images/${item.phone.image}" alt="${item.phone.phoneName}" onerror="this.src='images/no-image.jpg'" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                                </c:if>
                            </td>
                            <td data-label="Tên sản phẩm">
                                <c:if test="${item.phone != null}">
                                    <strong>${item.phone.phoneName}</strong>
                                </c:if>
                                <c:if test="${item.phone == null}">
                                    <span style="color: var(--text-light);">Sản phẩm đã bị xóa</span>
                                </c:if>
                            </td>
                            <td data-label="Giá"><fmt:formatNumber value="${item.orderDetail.price}" type="number" /> đ</td>
                            <td data-label="Số lượng">${item.orderDetail.quantity}</td>
                            <td data-label="Thành tiền"><strong><fmt:formatNumber value="${item.orderDetail.price * item.orderDetail.quantity}" type="number" /> đ</strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    
    <div style="margin-top: 2rem; display: flex; gap: 1rem; flex-wrap: wrap;">
        <a href="/order" class="btn">← Quay lại danh sách đơn hàng</a>
        <a href="/home" class="btn">Tiếp tục mua sắm</a>
    </div>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
