package servlet;

import daoimpl.Phones_Daoimpl;
import daoimpl.Users_Daoimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Phones;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private Users_Daoimpl users_Daoimpl = new Users_Daoimpl();
    private Phones_Daoimpl phones_Daoimpl = new Phones_Daoimpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Phones> listPhone = phones_Daoimpl.findAll();
        request.setAttribute("listPhone", listPhone);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("giohang")) {

        }
    }
}
