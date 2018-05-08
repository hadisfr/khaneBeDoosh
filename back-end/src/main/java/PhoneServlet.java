package main.java;

import com.google.gson.Gson;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/phone")
public class PhoneServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PhoneServlet.class.getName());

    /**
     * @api {get} /phone get phone number
     * @apiGroup House
     * @apiVersion 1.0.0
     * @apiDescription do required financial things get house's owner's phone number
     * @apiName getPhone
     * @apiParam {string} id house id
     * @apiSuccess {string/phoneNumber} phone
     * @apiSuccessExample {json} Success 200
     * {"phone":"686-04-0693"}
     * @apiError (Bad Request 400) {string} msg
     * @apiErrorExample {json} Bad Request 400
     * HTTP/1.1 400 Bad Request
     * {"msg":"Invalid Parameters: java.lang.IllegalArgumentException: missing id"}
     * @apiErrorExample Unauthorized 401
     * HTTP/1.1 401 Unauthorized
     * @apiErrorExample Payment Required 402
     * HTTP/1.1 402 Payment Required
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
            Individual currentUser = (Individual) request.getAttribute(AuthenticationFilter.UserKey);
            if (currentUser != null) {
                if (!request.getParameterMap().containsKey("id"))
                    throw new IllegalArgumentException("missing id");
                StringStringPair house_UserId = Utility.decryptHouseId(request.getParameter("id"));
                String houseId = house_UserId.getSecond();
                String ownerId = house_UserId.getFirst();
                House house = KhaneBeDoosh.getInstance().getHouseById(
                        house_UserId.getSecond(),
                        house_UserId.getFirst()
                );
                response.setStatus(
                        currentUser.hasPaidforHouse(houseId, ownerId) || (
                                currentUser.payForHouse(houseId, ownerId) && (IndividualMapper.update(currentUser) > 0)
                        )
                                ? HttpServletResponse.SC_OK
                                : HttpServletResponse.SC_PAYMENT_REQUIRED
                );
                if (response.getStatus() == HttpServletResponse.SC_OK)
                    res.put("phone", house.getPhone());
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IllegalArgumentException | JSONException | SQLException e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.put("msg", "Invalid Parameters: " + e.getMessage());
            response.getWriter().write((new Gson()).toJson(res));

        } catch (Exception e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.put("msg", e.getMessage());
            response.getWriter().write((new Gson()).toJson(res));
        }
    }
}
