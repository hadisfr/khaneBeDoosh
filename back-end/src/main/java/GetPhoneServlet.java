package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getPhone")
public class GetPhoneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, String> res = new HashMap<String, String>();
        try {
            Individual currentUser = ((Individual) KhaneBeDoosh.getInstance().getDefaultUser());
            if (currentUser != null) {
                if (!request.getParameterMap().containsKey("houseId"))
                    throw new IllegalArgumentException("missing houseId");
                String houseId = request.getParameter("houseId");
                if (!request.getParameterMap().containsKey("ownerId"))
                    throw new IllegalArgumentException("missing ownerId");
                int ownerId = Integer.parseInt(request.getParameter("ownerId"));
                House house = KhaneBeDoosh.getInstance().getHouseById(houseId, ownerId);
                response.setStatus(
                        currentUser.hasPaidforHouse(houseId, ownerId) || currentUser.payForHouse(houseId, ownerId)
                                ? HttpServletResponse.SC_OK
                                : HttpServletResponse.SC_PAYMENT_REQUIRED
                );
                if (response.getStatus() == HttpServletResponse.SC_OK)
                    res.put("phone", house.getPhone());
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.put("msg", "Invalid Parameters: " + e.toString());
            response.getWriter().write((new Gson()).toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(res));
        }
    }
}
