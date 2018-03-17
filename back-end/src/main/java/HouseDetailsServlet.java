package main.java;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HouseDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            House house = KhaneBeDoosh.getInstance().getHouseById(
                    request.getParameter("houseId"),
                    Integer.parseInt(request.getParameter("ownerId"))
            );
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(house);
            response.getWriter().write(jsonResponse);
//            request.setAttribute("house", house);
//            request.getRequestDispatcher("houseDetails.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msg", "خانه‌ای با این مشخصات پیدا نشد!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
