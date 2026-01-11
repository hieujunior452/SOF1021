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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
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
            List<CartItem> cartItemList = new ArrayList<>();
            long total = 0;
            
            if (cartItems != null) {
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
            }
            
            request.setAttribute("cartItems", cartItemList);
            request.setAttribute("total", total);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải giỏ hàng!");
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
            
            if ("add".equals(action)) {
                try {
                    int phoneId = Integer.parseInt(request.getParameter("phoneId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    
                    if (quantity <= 0) {
                        request.setAttribute("error", "Số lượng phải lớn hơn 0!");
                        doGet(request, response);
                        return;
                    }
                    
                    Phones phone = phonesDao.findById(phoneId);
                    if (phone == null) {
                        request.setAttribute("error", "Sản phẩm không tồn tại!");
                        doGet(request, response);
                        return;
                    }
                    
                    Cart existingCart = cartDao.findByUserAndPhone(user.getUserId(), phoneId);
                    if (existingCart != null) {
                        int newQuantity = existingCart.getQuantity() + quantity;
                        if (newQuantity > phone.getStock()) {
                            request.setAttribute("error", "Số lượng vượt quá tồn kho! Tồn kho còn: " + phone.getStock());
                            doGet(request, response);
                            return;
                        }
                        existingCart.setQuantity(newQuantity);
                        cartDao.update(existingCart);
                    } else {
                        if (quantity > phone.getStock()) {
                            request.setAttribute("error", "Số lượng vượt quá tồn kho! Tồn kho còn: " + phone.getStock());
                            doGet(request, response);
                            return;
                        }
                        Cart cart = new Cart();
                        cart.setUserId(user.getUserId());
                        cart.setPhoneId(phoneId);
                        cart.setQuantity(quantity);
                        cartDao.create(cart);
                    }
                    
                    response.sendRedirect("/cart");
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Thông tin không hợp lệ!");
                    doGet(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Có lỗi xảy ra khi thêm vào giỏ hàng!");
                    doGet(request, response);
                }
            } else if ("update".equals(action)) {
                try {
                    int cartId = Integer.parseInt(request.getParameter("cartId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    
                    if (quantity <= 0) {
                        request.setAttribute("error", "Số lượng phải lớn hơn 0!");
                        doGet(request, response);
                        return;
                    }
                    
                    Cart cart = cartDao.findById(cartId);
                    if (cart != null && cart.getUserId() == user.getUserId()) {
                        Phones phone = phonesDao.findById(cart.getPhoneId());
                        if (phone != null && quantity > phone.getStock()) {
                            request.setAttribute("error", "Số lượng vượt quá tồn kho! Tồn kho còn: " + phone.getStock());
                            doGet(request, response);
                            return;
                        }
                        cart.setQuantity(quantity);
                        cartDao.update(cart);
                    } else {
                        request.setAttribute("error", "Không tìm thấy sản phẩm trong giỏ hàng!");
                        doGet(request, response);
                        return;
                    }
                    
                    response.sendRedirect("/cart");
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Thông tin không hợp lệ!");
                    doGet(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Có lỗi xảy ra khi cập nhật giỏ hàng!");
                    doGet(request, response);
                }
            } else if ("delete".equals(action)) {
                try {
                    int cartId = Integer.parseInt(request.getParameter("cartId"));
                    Cart cart = cartDao.findById(cartId);
                    if (cart != null && cart.getUserId() == user.getUserId()) {
                        cartDao.deleteById(cartId);
                    }
                    response.sendRedirect("/cart");
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Thông tin không hợp lệ!");
                    doGet(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm!");
                    doGet(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra! Vui lòng thử lại.");
            doGet(request, response);
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
