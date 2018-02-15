package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Individual currentUser = (Individual) KhaneBeDoosh.getInstance().getDefaultUser();
        try {
            if(KhaneBeDoosh.getInstance().increaseBalance(
                    currentUser,
                    Integer.parseInt(request.getParameter("balance").equals("") ? "0" : request.getParameter("balance")))
                    )
                request.setAttribute("msg", "افزایش اعتبار موفقیت‌آمیز بود.");
            else
                request.setAttribute("msg", "افزایش اعتبار ناموفق بود.");
        } catch(Exception e) {
            request.setAttribute("msg", "استثنا: ‪" + e);
            request.getRequestDispatcher("/").forward(request, response);
        }
        request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request, response);
    }
}
