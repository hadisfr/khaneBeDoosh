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
            int maxSellPrice = request.getParameterMap().containsKey("maxSellPrice")
                    ? Integer.parseInt(request.getParameter("maxSellPrice"))
                    : 0;
            int maxBasePrice = request.getParameterMap().containsKey("maxBasePrice")
                    ? Integer.parseInt(request.getParameter("maxBasePrice"))
                    : 0;
            int maxRentPrice = request.getParameterMap().containsKey("maxRentPrice")
                    ? Integer.parseInt(request.getParameter("maxRentPrice"))
                    : 0;
            houses.addAll(KhaneBeDoosh.getInstance().filterHouses(buildingType, dealType, minArea, maxSellPrice));
            // TODO: handle default parameters
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write((new Gson()).toJson(houses));
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
