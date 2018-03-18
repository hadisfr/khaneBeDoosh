package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pay")
public class PayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Individual currentUser = (Individual) KhaneBeDoosh.getInstance().getDefaultUser();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        try {
            int balance = Integer.parseInt(request.getParameter("balance"));
            res.put("success", KhaneBeDoosh.getInstance().increaseBalance(currentUser, balance));
            response.setStatus(res.get("success") ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_GATEWAY);
            response.getWriter().write((new Gson()).toJson(res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
