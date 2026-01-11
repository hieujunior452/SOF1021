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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Users_Daoimpl usersDao = new Users_Daoimpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validate input
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // kiểm tra tài khoản
            if (usersDao.checkLogin(username.trim(), password.trim())) {
                // lấy thông tin user đầy đủ
                Users user = usersDao.findByUsername(username.trim());
                
                if (user != null) {
                    // lưu session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username.trim());
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("userFullName", user.getFullName());

                    response.sendRedirect("/home");
                } else {
                    request.setAttribute("error", "Không tìm thấy thông tin người dùng!");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi đăng nhập! Vui lòng thử lại.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}

