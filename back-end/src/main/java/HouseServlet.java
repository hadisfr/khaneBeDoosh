package main.java;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/house")
public class HouseServlet extends HttpServlet {

    /**
     * @api {get} /house get house details
     * @apiGroup House
     * @apiVersion 1.0.0
     * @apiName getHouse
     * @apiParam {string} id house id
     * @apiSuccess {string} id
     * @apiSuccess {string} address
     * @apiSuccess {int} area
     * @apiSuccess {string/enum} buildingType possible values: ["APARTMENT", "VILLA"]
     * @apiSuccess {string/enum} dealType possible values: ["SELL", "RENT"]
     * @apiSuccess {string} description
     * @apiSuccess {string/uri} imageUrl
     * @apiSuccess {json} price
     * @apiSuccess {int} price.basePrice iff `dealType == RENT`
     * @apiSuccess {int} price.sellPrice iff `dealType == SELL`
     * @apiSuccess {int} price.rentPrice iff `dealType == RENT`
     * @apiSuccessExample {json} Success 200
     * {
     * "imageUrl":
     * "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Soweto_township.jpg/320px-Soweto_township.jpg",
     * "id":"ca27a86c-bf52-4d79-9834-90a48ff4be9b_1001",
     * "area":161,
     * "buildingType":"APARTMENT",
     * "dealType":"SELL",
     * "price":{"sellPrice":109813},
     * "address":"دروازه غار",
     * "description":"از خانه برون آمد و بازار بیاراست، در وهم نگنجد که چه دلبند و چه شیرین"
     * }
     * @apiError (Bad Request 400) {string} msg
     * @apiErrorExample {json} Bad Request 400
     * HTTP/1.1 400 Bad Request
     * {"msg":"Invalid Parameters: java.lang.IllegalArgumentException: missing id"}
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
            if (!request.getParameterMap().containsKey("id"))
                throw new IllegalArgumentException("missing id");
            StringStringPair house_UserId = Utility.decryptHouseId(request.getParameter("id"));
            House house = KhaneBeDoosh.getInstance().getHouseById(
                    house_UserId.getSecond(),
                    house_UserId.getFirst()
            );
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write((new Gson()).toJson(new HouseDetailWrapper(house)));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }

    /**
     * @api {post} /house add new house
     * @apiGroup House
     * @apiVersion 1.0.0
     * @apiName postHouse
     * @apiParam {string} address
     * @apiParam {int} area
     * @apiParam {string/enum} buildingType possible values: ["APARTMENT", "VILLA"]
     * @apiParam {string/enum} dealType possible values: ["SELL", "RENT"]
     * @apiParam {string} description
     * @apiParam {string/phoneNumber)} phone
     * @apiParam {object} price
     * @apiParam {int} price.basePrice iff `dealType == RENT`
     * @apiParam {int} price.sellPrice iff `dealType == SELL`
     * @apiParam {int} price.rentPrice iff `dealType == RENT`
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
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String id = RandomStringUtils.randomAlphabetic(20);
            String imageUrl = "";
            User currentUser = KhaneBeDoosh.getInstance().getDefaultUser();
            if (currentUser != null) {
                if (!request.getParameterMap().containsKey("dealType"))
                    throw new IllegalArgumentException("missing dealType");
                DealType dealType = DealType.parseString(request.getParameter("dealType"));
                if (dealType == null)
                    throw new IllegalArgumentException("bad dealType");
                if (!request.getParameterMap().containsKey("buildingType"))
                    throw new IllegalArgumentException("missing buildingType");
                BuildingType buildingType = BuildingType.parseString(request.getParameter("buildingType"));
                if (buildingType == null)
                    throw new IllegalArgumentException("bad buildingType");
                if (!request.getParameterMap().containsKey("area"))
                    throw new IllegalArgumentException("missing area");
                int area = Integer.parseInt(request.getParameter("area"));
                if (!request.getParameterMap().containsKey("address"))
                    throw new IllegalArgumentException("missing address");
                String address = request.getParameter("address");
                if (!request.getParameterMap().containsKey("phone"))
                    throw new IllegalArgumentException("missing phone");
                String phone = request.getParameter("phone");
                String description = request.getParameterMap().containsKey("description")
                        ? request.getParameter("description")
                        : "";

                if (dealType == DealType.RENT) {
                    if (!request.getParameterMap().containsKey("rentPrice"))
                        throw new IllegalArgumentException("missing rentPrice");
                    int rentPrice = Integer.parseInt(request.getParameter("rentPrice"));
                    if (!request.getParameterMap().containsKey("basePrice"))
                        throw new IllegalArgumentException("missing basePrice");
                    int basePrice = Integer.parseInt(request.getParameter("basePrice"));
                    KhaneBeDoosh.getInstance().addHouse(
                            id,
                            area,
                            buildingType,
                            imageUrl,
                            currentUser,
                            rentPrice,
                            basePrice,
                            address,
                            phone,
                            description
                    );
                } else if (dealType == DealType.SELL) {
                    if (!request.getParameterMap().containsKey("sellPrice"))
                        throw new IllegalArgumentException("missing sellPrice");
                    int sellPrice = Integer.parseInt(request.getParameter("sellPrice"));
                    KhaneBeDoosh.getInstance().addHouse(
                            id,
                            area,
                            buildingType,
                            imageUrl,
                            currentUser,
                            sellPrice,
                            address,
                            phone,
                            description
                    );
                } else
                    throw new IllegalArgumentException("bad dealType");
                HashMap<String, Boolean> res = new HashMap<String, Boolean>();
                res.put("success", true);
                response.getWriter().write((new Gson()).toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.toString());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }
}
