package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pay")
public class PayServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PayServlet.class.getName());

    /**
     * @api {post} /pay increment user's balance
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiName postPay
     * @apiParam {int} balance amount to increment
     * @apiSuccess {bool} success
     * @apiSuccessExample {json} Success 200
     * {"success":true}
     * @apiError (Bad Request 400) {string} msg
     * @apiErrorExample {json} Bad Request 400
     * HTTP/1.1 400 Bad Request
     * {"msg":"Invalid Parameters: java.lang.IllegalArgumentException: missing id"}
     * @apiErrorExample Unauthorized 401
     * HTTP/1.1 401 Unauthorized
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        try {
            Individual currentUser = (Individual) KhaneBeDoosh.getInstance().getDefaultUser();
            if (currentUser != null) {
                if (!request.getParameterMap().containsKey("balance"))
                    throw new IllegalArgumentException("missing balance");
                int balance = Integer.parseInt(request.getParameter("balance"));
                res.put("success", KhaneBeDoosh.getInstance().increaseBalance(currentUser, balance));
                response.setStatus(
                        res.get("success") ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_GATEWAY
                );
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
