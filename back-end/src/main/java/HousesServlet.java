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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/houses")
public class HousesServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HousesServlet.class.getName());

    /**
     * @api {get} /houses get bought houses
     * @apiGroup House
     * @apiVersion 1.0.1
     * @apiName getHouses
     * @apiSuccess {object[]} _ array of objects with:
     * @apiSuccess {string} _.id
     * @apiSuccess {int} _.area
     * @apiSuccess {string/enum} _.buildingType possible values: ["APARTMENT", "VILLA"]
     * @apiSuccess {string/enum} _.dealType possible values: ["SELL", "RENT"]
     * @apiSuccess {string/uri} _.imageUrl
     * @apiSuccess {json} _.price
     * @apiSuccess {int} _.price.basePrice iff `dealType == RENT`
     * @apiSuccess {int} _.price.sellPrice iff `dealType == SELL`
     * @apiSuccess {int} _.price.rentPrice iff `dealType == RENT`
     * @apiSuccessExample {json} Success 200
     * {"success":true}
     * @apiErrorExample {json} Bad Request 400
     * HTTP/1.1 400 Bad Request
     * {"msg":"Invalid Parameters: java.lang.IllegalArgumentException: missing id"}
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
        try {
            Individual currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            if (currentUser != null) {
                if (currentUser.getIsAdmin()) {
                    HashMap<String, ArrayList<SearchHouseWrapper>> housesByUsers =
                            new HashMap<String, ArrayList<SearchHouseWrapper>>();
                    for (Individual user : KhaneBeDoosh.getInstance().getIndividuals()) {
                        housesByUsers.put(user.getUsername(), getHousesByUser(user));
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write((new Gson()).toJson(housesByUsers));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write((new Gson()).toJson(getHousesByUser(currentUser)));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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

    private ArrayList<SearchHouseWrapper> getHousesByUser(Individual user)
            throws SQLException, IOException, ClassNotFoundException {
        ArrayList<House> houses = new ArrayList<House>();
        for (StringStringPair pair : user.getPaidHouses()) {
            houses.add(KhaneBeDoosh.getInstance().getHouseById(pair.getSecond(), pair.getFirst()));
        }
        ArrayList<SearchHouseWrapper> wrappedHouses = new ArrayList<SearchHouseWrapper>();
        for (House house : houses) {
            wrappedHouses.add(new SearchHouseWrapper(house));
        }
        return wrappedHouses;
    }
}
