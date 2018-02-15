package main.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetPhoneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.getParameter("houseId"),
        // Integer.parseInt(request.getParameter("ownerId"))
        boolean canSeePhone = false;  // TODO: determine if user can see phone
        request.setAttribute("wantsToSeePhone", true);
        request.setAttribute("canSeePhone", canSeePhone);
        request.getRequestDispatcher("houseDetails.jsp").forward(request, response);
    }
}
