package servlet;

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
import model.OrderDetails;
import model.Orders;
import model.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
    
    private Orders_Daoimpl ordersDao = new Orders_Daoimpl();
    private OrderDetails_Daoimpl orderDetailsDao = new OrderDetails_Daoimpl();
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
            
            String orderIdStr = request.getParameter("id");
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                request.setAttribute("error", "ID đơn hàng không hợp lệ!");
                response.sendRedirect("/order");
                return;
            }
            
            try {
                int orderId = Integer.parseInt(orderIdStr);
                Orders order = ordersDao.findById(orderId);
                
                if (order == null) {
                    request.setAttribute("error", "Đơn hàng không tồn tại!");
                    response.sendRedirect("/order");
                    return;
                }
                
                if (order.getUserId() != user.getUserId()) {
                    request.setAttribute("error", "Bạn không có quyền xem đơn hàng này!");
                    response.sendRedirect("/order");
                    return;
                }
                
                List<OrderDetails> orderDetails = orderDetailsDao.findByOrderId(orderId);
                List<OrderDetailItem> items = new ArrayList<>();
                
                if (orderDetails != null) {
                    for (OrderDetails detail : orderDetails) {
                        OrderDetailItem item = new OrderDetailItem();
                        item.setOrderDetail(detail);
                        item.setPhone(phonesDao.findById(detail.getPhoneId()));
                        items.add(item);
                    }
                }
                
                request.setAttribute("order", order);
                request.setAttribute("items", items);
                request.getRequestDispatcher("/orderDetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID đơn hàng không hợp lệ!");
                response.sendRedirect("/order");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra! Vui lòng thử lại.");
            response.sendRedirect("/order");
        }
    }
    
    public static class OrderDetailItem {
        private OrderDetails orderDetail;
        private model.Phones phone;
        
        public OrderDetails getOrderDetail() {
            return orderDetail;
        }
        
        public void setOrderDetail(OrderDetails orderDetail) {
            this.orderDetail = orderDetail;
        }
        
        public model.Phones getPhone() {
            return phone;
        }
        
        public void setPhone(model.Phones phone) {
            this.phone = phone;
        }
    }
}
