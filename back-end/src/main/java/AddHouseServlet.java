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
        // TODO: should handle default params? if so, handle them properly.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String id = RandomStringUtils.randomAlphabetic(20);
            String imageUrl = "";
            String expireTime = "2032-12-01";
            User currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            DealType dealType = request.getParameterMap().containsKey("dealType")
                    ? DealType.parseString(request.getParameter("dealType"))
                    : DealType.SELL;
            int area = request.getParameterMap().containsKey("area")
                    ? Integer.parseInt(request.getParameter("area"))
                    : 0;
            BuildingType buildingType = request.getParameterMap().containsKey("buildingType")
                    ? BuildingType.parseString(request.getParameter("buildingType"))
                    : BuildingType.APARTMENT;
            if (buildingType == null)
                throw new IOException("Bad BuildingType");
            int price = request.getParameterMap().containsKey("price")
                    ? Integer.parseInt(request.getParameter("price"))
                    : 0;
            String address = request.getParameterMap().containsKey("address")
                    ? request.getParameter("address")
                    : "";
            String phone = request.getParameterMap().containsKey("phone")
                    ? request.getParameter("phone")
                    : "";
            String description = request.getParameterMap().containsKey("description")
                    ? request.getParameter("description")
                    : "";
            if (dealType == DealType.RENT) {
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        area,
                        buildingType,
                        imageUrl,
                        currentUser,
                        price,
                        0,
                        address,
                        phone,
                        description,
                        expireTime
                );
            } else if (dealType == DealType.SELL) {
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        area,
                        buildingType,
                        imageUrl,
                        currentUser,
                        price,
                        address,
                        phone,
                        description,
                        expireTime
                );
            } else
                throw new IOException("Bad DealType");
            HashMap<String, Boolean> res = new HashMap<String, Boolean>();
            res.put("success", true);
            response.getWriter().write((new Gson()).toJson(res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
