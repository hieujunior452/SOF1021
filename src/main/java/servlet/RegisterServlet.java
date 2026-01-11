package servlet;

import daoimpl.Users_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private Users_Daoimpl usersDao = new Users_Daoimpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            // Validate input
            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("error", "Tài khoản không được để trống!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("error", "Mật khẩu không được để trống!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Email không được để trống!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Kiểm tra username đã tồn tại chưa
            Users existingUser = usersDao.findByUsername(username.trim());
            if (existingUser != null) {
                request.setAttribute("error", "Tài khoản đã tồn tại! Vui lòng chọn tài khoản khác.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Tạo tài khoản mới
            usersDao.create(new Users(username.trim(), password.trim(), email.trim()));
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi đăng ký! Vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

