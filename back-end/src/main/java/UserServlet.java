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

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    /**
     * @api {get} /user get current user's info
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiName getUser
     * @apiSuccess {int} balance
     * @apiSuccess {string} name
     * @apiSuccess {string} username
     * @apiSuccessExample {json} Success 200
     * {"balance":"200","name":"بهنام همایون","username":"behnam"}
     * @apiErrorExample Unauthorized 401
     * HTTP/1.1 401 Unauthorized
     * @apiError (Internal Server Error 500) {string} msg
     * @apiErrorExample {json} Internal Server Error 500
     * HTTP/1.1 500 Internal Server Error
     * {"msg":"Server Error details"}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HashMap<String, String> res = new HashMap<String, String>();
        try {
            Individual currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            if (currentUser != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                res.put("name", currentUser.getDisplayName());
                res.put("username", currentUser.getUsername());
                res.put("balance", "" + currentUser.getBalance());
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            res.put("msg", e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write((new Gson()).toJson(res));
        }
    }
}
