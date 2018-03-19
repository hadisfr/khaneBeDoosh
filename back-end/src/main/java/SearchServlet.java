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
                    : null;
            DealType dealType = request.getParameterMap().containsKey("dealType")
                    ? DealType.parseString(request.getParameter("dealType"))
                    : null;
            int minArea = request.getParameterMap().containsKey("minArea")
                    ? Integer.parseInt(request.getParameter("minArea"))
                    : Utility.illegalSearchValue;
            Price price = null;
            if(request.getParameterMap().containsKey("maxSellPrice")){
                price = new PriceSell(Integer.parseInt(request.getParameter("maxSellPrice")));
            } else if (request.getParameterMap().containsKey("maxBasePrice") ||
                    request.getParameterMap().containsKey("maxRentPrice")) {
                price = new PriceRent(
                        request.getParameterMap().containsKey("maxBasePrice")
                        ? Integer.parseInt(request.getParameter("maxBasePrice"))
                        : Utility.illegalSearchValue,
                        request.getParameterMap().containsKey("maxRentPrice")
                        ? Integer.parseInt(request.getParameter("maxRentPrice"))
                        : Utility.illegalSearchValue
                );
            }
            houses.addAll(KhaneBeDoosh.getInstance().filterHouses(buildingType, dealType, minArea, price));
            ArrayList<SearchHouseWrapper> wrappedHouses = new ArrayList<SearchHouseWrapper>();
            for(House house : houses){
                wrappedHouses.add(new SearchHouseWrapper(house));
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write((new Gson()).toJson(wrappedHouses));
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
