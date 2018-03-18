package main.java;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pay")
public class PayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Individual currentUser = (Individual) KhaneBeDoosh.getInstance().getDefaultUser();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse;
        try {
            int balance = Integer.parseInt(request.getParameter("balance"));
            if (balance < 0)
                balance = 0;
            if (KhaneBeDoosh.getInstance().increaseBalance(currentUser, balance)) {
                jsonResponse = "{ \"success\": true}";
                response.setStatus(HttpServletResponse.SC_OK);
//                request.setAttribute("msg", "افزایش اعتبار موفقیت‌آمیز بود.");
            } else {
                jsonResponse = "{ \"success\": false}";
                response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
//                request.setAttribute("msg", "افزایش اعتبار ناموفق بود.");
            }
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            request.setAttribute("msg", "استثنا: ‪" + e);
            request.getRequestDispatcher("/").forward(request, response);
        }
//        request.getRequestDispatcher("/").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request, response);
    }
}
