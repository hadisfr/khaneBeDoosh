package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddHouseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DealType dealType = DealType.parseString(request.getParameter("dealType"));
        // try {
        //     if(DealType == DealType.RENT) {
        //         KhaneBeDoosh.getInstance().addHouse(
        //             "String id,",
        //             Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
        //             BuildingType.parseString(request.getParameter("buildingType")),
        //             "",
        //             null,
        //             Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price")),
        //             0
        //         );
        //     } else if(buildingType == BuildingType.SELL) {
        //         KhaneBeDoosh.getInstance().addHouse(
        //             "String id,",
        //             Integer.parseInt(request.getParameter("area") == "" ? "0" : request.getParameter("area")),
        //             BuildingType.parseString(request.getParameter("buildingType")),
        //             "",
        //             null,
        //             Integer.parseInt(request.getParameter("price") == "" ? "0" : request.getParameter("price"))
        //         );
        //     } else
        //         throw new IOException("Bad BuildingType");
        // } catch(Exception e) {
        //     request.setAttribute("msg", "استثنا: ‪" + e);
        //     request.getRequestDispatcher("/").forward(request, response);
        // }
        request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
