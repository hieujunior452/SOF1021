package servlet;

import daoimpl.Users_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // kiểm tra tài khoản
        if (usersDao.checkLogin(username, password)) {

            // lưu session
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            request.getRequestDispatcher("/index.jsp").forward(request, response);
            System.out.println("done");
        } else {
            System.out.println("failed");
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}

