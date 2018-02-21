package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mohammadreza on 2/22/2018 AD.
 */
public class HouseDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            House house = KhaneBeDoosh.getInstance().getHouseById(
                    request.getParameter("houseId"),
                    Integer.parseInt(request.getParameter("ownerId"))
            );
            request.setAttribute("house", house);
            request.getRequestDispatcher("houseDetails.jsp").forward(request, response);
        } catch(Exception e) {
            request.setAttribute("msg", "خانه‌ای با این مشخصات پیدا نشد!" );
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
