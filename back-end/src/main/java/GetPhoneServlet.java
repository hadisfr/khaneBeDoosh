package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetPhoneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            House house = KhaneBeDoosh.getInstance().getHouseById(
                    request.getParameter("houseId"),
                    Integer.parseInt(request.getParameter("ownerId"))
            );
            request.setAttribute("house", house);
            Individual currentUser = ((Individual) KhaneBeDoosh.getInstance().getDefaultUser());
            String houseId = request.getParameter("houseId");
            int ownerId = Integer.parseInt(request.getParameter("ownerId"));
            request.setAttribute("wantsToSeePhone", true);
            request.setAttribute("canSeePhone",
                    currentUser.hasPaidforHouse(houseId, ownerId) || currentUser.payForHouse(houseId, ownerId));
            request.getRequestDispatcher("houseDetails.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msg", "خانه‌ای با این مشخصات پیدا نشد!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
