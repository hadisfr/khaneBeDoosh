package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang.RandomStringUtils;

public class AddHouseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DealType dealType = DealType.parseString(request.getParameter("dealType"));
        try {
            String id = RandomStringUtils.randomAlphabetic(10);
            String imageUrl = "";
            String expireTime = "2032-12-01";
            User user = KhaneBeDoosh.getInstance().getDefaultUser();
            if(dealType == DealType.RENT) {
                KhaneBeDoosh.getInstance().addHouse(
                    id,
                    Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
                    BuildingType.parseString(request.getParameter("buildingType")),
                    imageUrl,
                    user,
                    Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price")),
                    0,
                    request.getParameter("address"),
                    request.getParameter("phone"),
                    request.getParameter("description"),
                    expireTime
                );
                request.setAttribute("msg", "خانه با موفقیت اضافه شد.");
            } else if(dealType == DealType.SELL) {
                KhaneBeDoosh.getInstance().addHouse(
                    id,
                    Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
                    BuildingType.parseString(request.getParameter("buildingType")),
                    imageUrl,
                    user,
                    Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price")),
                    request.getParameter("address"),
                    request.getParameter("phone"),
                    request.getParameter("description"),
                    expireTime
                );
                request.setAttribute("msg", "خانه با موفقیت اضافه شد.");
            } else
                throw new IOException("Bad BuildingType");
        } catch(Exception e) {
            request.setAttribute("msg", "استثنا: ‪" + e);
            request.getRequestDispatcher("/").forward(request, response);
        }
        request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
