package servlet;

import daoimpl.Brands_Daoimpl;
import daoimpl.Phones_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brands;
import model.Phones;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private Phones_Daoimpl phonesDao = new Phones_Daoimpl();
    private Brands_Daoimpl brandsDao = new Brands_Daoimpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Phones> listPhone = phonesDao.findAll();
            List<Brands> listBrands = brandsDao.findAll();
            
            String brandId = request.getParameter("brandId");
            if (brandId != null && !brandId.isEmpty()) {
                try {
                    int id = Integer.parseInt(brandId);
                    listPhone = phonesDao.findByBrand(id);
                } catch (NumberFormatException e) {
                    // ignore invalid brandId
                }
            }
            
            if (listPhone == null) {
                listPhone = new java.util.ArrayList<>();
            }
            if (listBrands == null) {
                listBrands = new java.util.ArrayList<>();
            }
            
            request.setAttribute("listPhone", listPhone);
            request.setAttribute("listBrands", listBrands);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải dữ liệu!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
