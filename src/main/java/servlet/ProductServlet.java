package servlet;

import daoimpl.Phones_Daoimpl;
import daoimpl.Brands_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Phones;
import model.Brands;

import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    
    private Phones_Daoimpl phonesDao = new Phones_Daoimpl();
    private Brands_Daoimpl brandsDao = new Brands_Daoimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            
            if (id == null || id.isEmpty()) {
                response.sendRedirect("/home");
                return;
            }
            
            try {
                int phoneId = Integer.parseInt(id);
                Phones phone = phonesDao.findById(phoneId);
                
                if (phone == null) {
                    request.setAttribute("error", "Sản phẩm không tồn tại!");
                    response.sendRedirect("/home");
                    return;
                }
                
                Brands brand = null;
                if (phone.getBrandId() > 0) {
                    brand = brandsDao.findById(phone.getBrandId());
                }
                
                request.setAttribute("phone", phone);
                request.setAttribute("brand", brand);
                request.getRequestDispatcher("/product.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID sản phẩm không hợp lệ!");
                response.sendRedirect("/home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra! Vui lòng thử lại.");
            response.sendRedirect("/home");
        }
    }
}
