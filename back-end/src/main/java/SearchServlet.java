package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<House> houses = new ArrayList<House>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            BuildingType buildingType = request.getParameterMap().containsKey("buildingType")
                    ? BuildingType.parseString(request.getParameter("buildingType"))
                    : BuildingType.APARTMENT;
            DealType dealType = request.getParameterMap().containsKey("dealType")
                    ? DealType.parseString(request.getParameter("dealType"))
                    : DealType.SELL;
            int minArea = request.getParameterMap().containsKey("minArea")
                    ? Integer.parseInt(request.getParameter("minArea"))
                    : 0;
            int maxPrice = request.getParameterMap().containsKey("maxPrice")
                    ? Integer.parseInt(request.getParameter("maxPrice"))
                    : 0;
            houses.addAll(KhaneBeDoosh.getInstance().filterHouses(buildingType, dealType, minArea, maxPrice));
            // TODO: handle default parameters
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write((new Gson()).toJson(houses));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
