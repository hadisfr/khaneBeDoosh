package main.java;

import com.google.gson.Gson;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    /**
     * @api {get} /login login
     * @apiGroup User
     * @apiVersion 1.1.0
     * @apiName getLogin
     * @apiParam {String} username
     * @apiParam {String} password
     * @apiSuccess {String} token
     * @apiSuccessExample {json} Success 200
     * {"token": eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImJlaG7hbSJ9.-1PZN9JhUkVdjEK3PVQns9IlO83VF98K-ufXnH-dqkM}
     * @apiError (Bad Request 400) {string} msg
     * @apiErrorExample {json} Bad Request 400
     * HTTP/1.1 400 Bad Request
     * {"msg":"Invalid Parameters: java.lang.IllegalArgumentException: missing id"}
     * @apiErrorExample Unauthorized 401
     * HTTP/1.1 401 Unauthorized
     * @apiError (Forbidden 403) {bool} success false, invalid username or password
     * @apiErrorExample {json} Forbidden 403
     * HTTP/1.1 403 Forbidden
     * {"success":false}
     * @apiError (Internal Server Error 500) {string} msg
     * @apiErrorExample {json} Internal Server Error 500
     * HTTP/1.1 500 Internal Server Error
     * {"msg":"Server Error details"}
     * @apiError (Bad Gateway 502) {bool} success
     * @apiErrorExample {json} Bad Gateway 502
     * HTTP/1.1 502 Bad Gateway
     * {"success":false}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String token = KhaneBeDoosh.getInstance().getToken(request.getParameter("username"),
                    request.getParameter("password"));
            if (token != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                HashMap<String, String> res = new HashMap<String, String>();
                res.put("token", token);
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                HashMap<String, Boolean> res = new HashMap<String, Boolean>();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.put("success", false);
                response.getWriter().write((new Gson()).toJson(res));
            }
        } catch (IllegalArgumentException | JSONException | SQLException e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.getMessage());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.getMessage());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
