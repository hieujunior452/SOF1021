package servlet;

import daoimpl.Cart_Daoimpl;
import daoimpl.Phones_Daoimpl;
import daoimpl.Users_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Phones;
import model.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
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
            
            List<Cart> cartItems = cartDao.findByUserId(user.getUserId());
            
            if (cartItems == null || cartItems.isEmpty()) {
                request.setAttribute("error", "Giỏ hàng trống!");
                request.getRequestDispatcher("/cart").forward(request, response);
                return;
            }
            
            List<CartItem> cartItemList = new ArrayList<>();
            long total = 0;
            
            for (Cart cart : cartItems) {
                Phones phone = phonesDao.findById(cart.getPhoneId());
                if (phone != null) {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setPhone(phone);
                    item.setSubtotal(phone.getPrice() * cart.getQuantity());
                    cartItemList.add(item);
                    total += item.getSubtotal();
                }
            }
            
            request.setAttribute("user", user);
            request.setAttribute("cartItems", cartItemList);
            request.setAttribute("total", total);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải trang thanh toán!");
            response.sendRedirect("/cart");
        }
    }
    
    public static class CartItem {
        private Cart cart;
        private Phones phone;
        private long subtotal;
        
        public Cart getCart() {
            return cart;
        }
        
        public void setCart(Cart cart) {
            this.cart = cart;
        }
        
        public Phones getPhone() {
            return phone;
        }
        
        public void setPhone(Phones phone) {
            this.phone = phone;
        }
        
        public long getSubtotal() {
            return subtotal;
        }
        
        public void setSubtotal(long subtotal) {
            this.subtotal = subtotal;
        }
    }
}
