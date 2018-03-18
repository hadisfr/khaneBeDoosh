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
        try {
            String id = RandomStringUtils.randomAlphabetic(20);
            String imageUrl = "";
            String expireTime = "2032-12-01";
            User currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            if (!request.getParameterMap().containsKey("dealType"))
                throw new IllegalArgumentException("missing dealType");
            DealType dealType = DealType.parseString(request.getParameter("dealType"));
            if (dealType == null)
                throw new IllegalArgumentException("bad dealType");
            if (!request.getParameterMap().containsKey("buildingType"))
                throw new IllegalArgumentException("missing buildingType");
            BuildingType buildingType = BuildingType.parseString(request.getParameter("buildingType"));
            if (buildingType == null)
                throw new IllegalArgumentException("bad buildingType");
            if (!request.getParameterMap().containsKey("area"))
                throw new IllegalArgumentException("missing area");
            int area = Integer.parseInt(request.getParameter("area"));
            if (!request.getParameterMap().containsKey("address"))
                throw new IllegalArgumentException("missing address");
            String address = request.getParameter("address");
            if (!request.getParameterMap().containsKey("phone"))
                throw new IllegalArgumentException("missing phone");
            String phone = request.getParameter("phone");
            if (!request.getParameterMap().containsKey("description"))
                throw new IllegalArgumentException("missing description");
            String description = request.getParameter("description");

            if (dealType == DealType.RENT) {
                if (!request.getParameterMap().containsKey("rentPrice"))
                    throw new IllegalArgumentException("missing rentPrice");
                int rentPrice = Integer.parseInt(request.getParameter("rentPrice"));
                if (!request.getParameterMap().containsKey("basePrice"))
                    throw new IllegalArgumentException("missing basePrice");
                int basePrice = Integer.parseInt(request.getParameter("basePrice"));
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        area,
                        buildingType,
                        imageUrl,
                        currentUser,
                        rentPrice,
                        basePrice,
                        address,
                        phone,
                        description,
                        expireTime
                );
            } else if (dealType == DealType.SELL) {
                if (!request.getParameterMap().containsKey("sellPrice"))
                    throw new IllegalArgumentException("missing sellPrice");
                int sellPrice = Integer.parseInt(request.getParameter("sellPrice"));
                KhaneBeDoosh.getInstance().addHouse(
                        id,
                        area,
                        buildingType,
                        imageUrl,
                        currentUser,
                        sellPrice,
                        address,
                        phone,
                        description,
                        expireTime
                );
            } else
                throw new IllegalArgumentException("bad dealType");
            HashMap<String, Boolean> res = new HashMap<String, Boolean>();
            res.put("success", true);
            response.getWriter().write((new Gson()).toJson(res));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
