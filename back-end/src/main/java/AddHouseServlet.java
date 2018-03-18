package main.java;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;

@WebServlet("/addHouse")
public class AddHouseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        DealType dealType = DealType.parseString(request.getParameter("dealType"));
        try {
            String id = RandomStringUtils.randomAlphabetic(20);
            String imageUrl = "";
            String expireTime = "2032-12-01";
            User currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            if (dealType == DealType.RENT) {
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
                        BuildingType.parseString(request.getParameter("buildingType")),
                        imageUrl,
                        currentUser,
                        Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price")),
                        0,
                        request.getParameter("address"),
                        request.getParameter("phone"),
                        request.getParameter("description"),
                        expireTime
                );
                request.setAttribute("msg", "خانه با موفقیت اضافه شد.");
            } else if (dealType == DealType.SELL) {
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
                        BuildingType.parseString(request.getParameter("buildingType")),
                        imageUrl,
                        currentUser,
                        Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price")),
                        request.getParameter("address"),
                        request.getParameter("phone"),
                        request.getParameter("description"),
                        expireTime
                );
                request.setAttribute("msg", "خانه با موفقیت اضافه شد.");
            } else
                throw new IOException("Bad BuildingType");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
