<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.House" %>
<%@ page import="main.java.DealType" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="haed.jsp" />
    </head>
    <body>
        <jsp:include page="haeder.jsp" />
        <% if(request.getAttribute("houses") != null) { %>
            <% for (House house : (ArrayList<House>)(request.getAttribute("houses"))) { %>
                <div class="outbox grid_element">
                    <% if(house.getDealType() == DealType.RENT) { %>
                        قیمت پایه: <%= house.getBasePrice() %> تومان
                        قیمیت اجاره: <%= house.getRentPrice() %> تومان
                    <% } else  { %>
                        قیمت خرید: <%= house.getSellPrice() %>
                    <% } %>
                    متراژ: <%= house.getArea() %> متر
                    نوع: <%= house.getBuildingType() %>
                    لینک عکس: <%= house.getImageUrl() %>
                    <a href="javascript: void(0);">اطلاعات بیشتر</a>
                </div>
            <% } %>
        <% } else { %>
            <div class="outbox">No Result Found.</div>
        <% } %>
    </body>
</html>
