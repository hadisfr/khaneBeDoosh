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
                        <div>قیمت پایه: <%= house.getBasePrice() %> تومان</div>
                        <div>قیمیت اجاره: <%= house.getRentPrice() %> تومان</div>
                    <% } else if(house.getDealType() == DealType.BUY) { %>
                        <div>قیمت خرید: <%= house.getSellPrice() %></div>
                    <% } %>
                    <div>متراژ: <%= house.getArea() %> متر</div>
                    <div>نوع: <%= house.getBuildingType() %></div>
                    <div>لینک عکس: <a href='<%= house.getImageUrl() %>' target="_blank"><%= house.getImageUrl() %></a></div>
                    <a href="javascript: void(0);">اطلاعات بیشتر</a>
                </div>
            <% } %>
        <% } else { %>
            <div class="outbox">نتیجه‌ای پیدا نشد.</div>
        <% } %>
    </body>
</html>
