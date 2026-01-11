package servlet;

import daoimpl.Cart_Daoimpl;
import daoimpl.OrderDetails_Daoimpl;
import daoimpl.Orders_Daoimpl;
import daoimpl.Phones_Daoimpl;
import daoimpl.Users_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.OrderDetails;
import model.Orders;
import model.Users;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    
    private Orders_Daoimpl ordersDao = new Orders_Daoimpl();
    private OrderDetails_Daoimpl orderDetailsDao = new OrderDetails_Daoimpl();
    private Cart_Daoimpl cartDao = new Cart_Daoimpl();
    private Phones_Daoimpl phonesDao = new Phones_Daoimpl();
    private Users_Daoimpl usersDao = new Users_Daoimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("user");
            
            if (username == null) {
                response.sendRedirect("/login");
                return;
            }
            
            Users user = usersDao.findByUsername(username);
            if (user == null) {
                response.sendRedirect("/login");
                return;
            }
            
            List<Orders> orders = ordersDao.findByUserId(user.getUserId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/orders.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải đơn hàng!");
            response.sendRedirect("/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("user");
            
            if (username == null) {
                response.sendRedirect("/login");
                return;
            }
            
            Users user = usersDao.findByUsername(username);
            if (user == null) {
                response.sendRedirect("/login");
                return;
            }
            
            if ("checkout".equals(action)) {
                try {
                    String address = request.getParameter("address");
                    
                    if (address == null || address.trim().isEmpty()) {
                        request.setAttribute("error", "Địa chỉ không được để trống!");
                        request.getRequestDispatcher("/checkout").forward(request, response);
                        return;
                    }
                    
                    List<Cart> cartItems = cartDao.findByUserId(user.getUserId());
                    
                    if (cartItems == null || cartItems.isEmpty()) {
                        request.setAttribute("error", "Giỏ hàng trống!");
                        request.getRequestDispatcher("/cart").forward(request, response);
                        return;
                    }
                    
                    // Kiểm tra tồn kho trước khi đặt hàng
                    for (Cart cart : cartItems) {
                        var phone = phonesDao.findById(cart.getPhoneId());
                        if (phone == null) {
                            request.setAttribute("error", "Một số sản phẩm không còn tồn tại!");
                            request.getRequestDispatcher("/checkout").forward(request, response);
                            return;
                        }
                        if (cart.getQuantity() > phone.getStock()) {
                            request.setAttribute("error", "Sản phẩm " + phone.getPhoneName() + " chỉ còn " + phone.getStock() + " sản phẩm!");
                            request.getRequestDispatcher("/checkout").forward(request, response);
                            return;
                        }
                    }
                    
                    long total = 0;
                    for (Cart cart : cartItems) {
                        var phone = phonesDao.findById(cart.getPhoneId());
                        if (phone != null) {
                            total += phone.getPrice() * cart.getQuantity();
                        }
                    }
                    
                    if (total <= 0) {
                        request.setAttribute("error", "Tổng tiền không hợp lệ!");
                        request.getRequestDispatcher("/checkout").forward(request, response);
                        return;
                    }
                    
                    Orders order = new Orders();
                    order.setUserId(user.getUserId());
                    order.setTotal(total);
                    order.setStatus("Đã đặt hàng");
                    order.setOrderDate(new Date());
                    order.setAddress(address.trim());
                    
                    ordersDao.create(order);
                    
                    int orderId = ordersDao.getLastInsertId(user.getUserId(), total, address.trim());
                    
                    if (orderId > 0) {
                        for (Cart cart : cartItems) {
                            var phone = phonesDao.findById(cart.getPhoneId());
                            if (phone != null) {
                                OrderDetails detail = new OrderDetails();
                                detail.setOrderId(orderId);
                                detail.setPhoneId(cart.getPhoneId());
                                detail.setQuantity(cart.getQuantity());
                                detail.setPrice(phone.getPrice());
                                orderDetailsDao.create(detail);
                                
                                cartDao.deleteById(cart.getCartId());
                            }
                        }
                        response.sendRedirect("/order?success=1");
                    } else {
                        request.setAttribute("error", "Có lỗi xảy ra khi tạo đơn hàng! Vui lòng thử lại.");
                        request.getRequestDispatcher("/checkout").forward(request, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Có lỗi xảy ra khi đặt hàng! Vui lòng thử lại.");
                    request.getRequestDispatcher("/checkout").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra! Vui lòng thử lại.");
            response.sendRedirect("/home");
        }
    }
}
