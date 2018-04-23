package main.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchServlet.class.getName());

    /**
     * @api {get} /search search houses
     * @apiGroup House
     * @apiVersion 1.0.0
     * @apiName getSearch
     * @apiParam {int} [minArea]
     * @apiParam {string/enum} [buildingType] possible values: ["APARTMENT", "VILLA"]
     * @apiParam {string/enum} [dealType] possible values: ["SELL", "RENT"]
     * @apiParam {int} [minBasePrice] only if `dealType == RENT`
     * @apiParam {int} [minSellPrice] only if `dealType == SELL`
     * @apiParam {int} [minRentPrice] only if `dealType == RENT`
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
        ArrayList<House> houses = new ArrayList<House>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            BuildingType buildingType = request.getParameterMap().containsKey("buildingType")
                    ? BuildingType.parseString(request.getParameter("buildingType"))
                    : null;
            DealType dealType = request.getParameterMap().containsKey("dealType")
                    ? DealType.parseString(request.getParameter("dealType"))
                    : null;
            int minArea = request.getParameterMap().containsKey("minArea")
                    ? Integer.parseInt(request.getParameter("minArea"))
                    : Utility.illegalSearchValue;
            Price price = null;
            if (request.getParameterMap().containsKey("maxSellPrice")) {
                price = new PriceSell(Integer.parseInt(request.getParameter("maxSellPrice")));
            } else if (request.getParameterMap().containsKey("maxBasePrice") ||
                    request.getParameterMap().containsKey("maxRentPrice")) {
                price = new PriceRent(
                        request.getParameterMap().containsKey("maxBasePrice")
                                ? Integer.parseInt(request.getParameter("maxBasePrice"))
                                : Utility.illegalSearchValue,
                        request.getParameterMap().containsKey("maxRentPrice")
                                ? Integer.parseInt(request.getParameter("maxRentPrice"))
                                : Utility.illegalSearchValue
                );
            }
            houses.addAll(KhaneBeDoosh.getInstance().searchHouses(buildingType, dealType, minArea, price));
            ArrayList<SearchHouseWrapper> wrappedHouses = new ArrayList<SearchHouseWrapper>();
            for (House house : houses) {
                wrappedHouses.add(new SearchHouseWrapper(house));
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write((new Gson()).toJson(wrappedHouses));
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
