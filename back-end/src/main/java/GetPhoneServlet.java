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
        try {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            HashMap<String, Boolean> res = new HashMap<String, Boolean>();
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
//            response.getWriter().write((new Gson()).toJson(res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
