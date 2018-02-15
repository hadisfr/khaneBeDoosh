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
        ArrayList<House> houses = new ArrayList<House>();
        houses.addAll(KhaneBeDoosh.getInstance().filterHouses(
                BuildingType.parseString(request.getParameter("buildingType")),
                DealType.parseString(request.getParameter("dealType")),
                Integer.parseInt(request.getParameter("minArea")),
                Integer.parseInt(request.getParameter("maxPrice"))
        ));
        request.setAttribute("houses", houses);
        request.getRequestDispatcher("searchResults.jsp").forward(request, response);
    }
}
