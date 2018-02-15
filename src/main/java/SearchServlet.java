package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<House> houses = KhaneBeDoosh.filterHouses(
            (BuildingType)(request.getParameter("buildingType")),
            (DealType)(request.getParameter("dealType")),
            (int)(request.getParameter("minArea")),
            (int)(request.getParameter("maxPrice"))
        );
        House house = new House("398y2iuwjndwksfsd", 200, BuildingType.APARTMENT, "http://google.com",
                DealType.BUY, 10, 20, 30, null);
        houses.add(house);
        request.setAttribute("houses", houses);
        request.getRequestDispatcher("searchResults.jsp").forward(request, response);
    }
}
