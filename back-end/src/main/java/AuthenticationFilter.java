package main.java;

import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    private String encoding;

    public static final String UserKey = "USER";

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info(String.format("req: %s %s", req.getMethod(), req.getRequestURI()));
        try {
            Individual user = null;
            String token = req.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                token = token.substring(6);
                try {
                    String username = Utility.getUsernameFromToken(token);
                    user = (Individual) KhaneBeDoosh.getInstance().getUserById(username);
                } catch (IllegalArgumentException e) {
                    logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
                    HashMap<String, String> err_res = new HashMap<String, String>();
                    err_res.put("msg", e.getMessage());
                    response.getWriter().write((new Gson()).toJson(err_res));
                }
            }
            request.setAttribute(UserKey, user);
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            next.doFilter(request, response);
        } catch (JSONException | SQLException e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", "Invalid Parameters: " + e.getMessage());
            response.getWriter().write((new Gson()).toJson(err_res));
        } catch (Exception e) {
            logger.warning(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HashMap<String, String> err_res = new HashMap<String, String>();
            err_res.put("msg", e.getMessage());
            response.getWriter().write((new Gson()).toJson(err_res));
        }
    }

    public void destroy() {
    }
}
