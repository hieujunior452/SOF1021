<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - Bee Phone</title>
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
    <h2 style="margin-bottom: 1rem;">Giỏ hàng của bạn</h2>
    
    <c:if test="${error != null}">
        <div class="alert alert-error">
            ${error}
        </div>
    </c:if>
    
    <c:if test="${cartItems != null && !empty cartItems}">
        <div style="overflow-x: auto;">
            <table>
                <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td data-label="Hình ảnh">
                                <img src="images/${item.phone.image}" alt="${item.phone.phoneName}" onerror="this.src='images/no-image.jpg'" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
                            </td>
                            <td data-label="Tên sản phẩm">
                                <a href="/product?id=${item.phone.phoneId}" style="color: var(--primary-color); text-decoration: none; font-weight: 600;">${item.phone.phoneName}</a>
                            </td>
                            <td data-label="Giá"><fmt:formatNumber value="${item.phone.price}" type="number" /> đ</td>
                            <td data-label="Số lượng">
                                <form action="/cart" method="post" style="display: flex; gap: 0.5rem; align-items: center; flex-wrap: wrap;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartId" value="${item.cart.cartId}">
                                    <input type="number" name="quantity" value="${item.cart.quantity}" min="1" max="${item.phone.stock}" class="quantity-input" required style="width: 80px;">
                                    <button type="submit" class="btn" style="padding: 0.5rem 1rem; font-size: 0.9rem;">Cập nhật</button>
                                </form>
                            </td>
                            <td data-label="Thành tiền"><strong><fmt:formatNumber value="${item.subtotal}" type="number" /> đ</strong></td>
                            <td data-label="Thao tác">
                                <form action="/cart" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="cartId" value="${item.cart.cartId}">
                                    <button type="submit" class="btn btn-danger" style="padding: 0.5rem 1rem; font-size: 0.9rem;" onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?')">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="total-section">
            <h3>Tổng tiền: <fmt:formatNumber value="${total}" type="number" /> đ</h3>
            <a href="/checkout" class="btn btn-success" style="width: 100%; padding: 1rem; font-size: 1.2rem; text-align: center; display: block; text-decoration: none;">Đặt hàng</a>
            <div style="margin-top: 1rem; text-align: center;">
                <a href="/home" class="btn" style="width: 100%; display: inline-block; text-align: center; text-decoration: none;">Tiếp tục mua sắm</a>
            </div>
        </div>
    </c:if>
    
    <c:if test="${cartItems == null || empty cartItems}">
        <div class="empty-cart">
            <h2>Giỏ hàng trống</h2>
            <p>Hãy thêm sản phẩm vào giỏ hàng để tiếp tục mua sắm!</p>
            <a href="/home" class="btn" style="margin-top: 1rem;">Tiếp tục mua sắm</a>
        </div>
    </c:if>
</div>

<footer class="footer">
    <p>© 2025, Bee Phone. All rights reserved.</p>
</footer>

</body>
</html>
