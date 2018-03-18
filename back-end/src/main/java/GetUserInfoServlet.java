package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, String> res = new HashMap<String, String>();
        try {
            Individual currentUser = ((Individual) KhaneBeDoosh.getInstance().getDefaultUser());
            if (currentUser != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                res.put("name", currentUser.getName());
                res.put("username", currentUser.getUsername());
                res.put("balance", "" + currentUser.getBalance());
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(res));
        }
    }
}
