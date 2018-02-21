<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.House" %>
<%@ page import="main.java.HouseRent" %>
<%@ page import="main.java.HouseSell" %>
<%@ page import="main.java.DealType" %>
<%@ page import="main.java.KhaneBeDoosh" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="haed.jsp" />
    </head>
    <body>
        <jsp:include page="haeder.jsp" />
        <%
            ArrayList<House> houses = (ArrayList<House>)(request.getAttribute("houses"));
            if(houses.size() > 0) { %>
            <% for (House house : houses) { %>
                <div class="outbox grid_element">
                    <% if(house.getDealType() == DealType.RENT) { %>
                        <div>قیمت پایه: <%= ((HouseRent)house).getBasePrice() %> تومان</div>
                        <div>قیمیت اجاره: <%= ((HouseRent)house).getRentPrice() %> تومان</div>
                    <% } else if(house.getDealType() == DealType.SELL) { %>
                        <div>قیمت خرید: <%= ((HouseSell)house).getSellPrice() %></div>
                        <div>&nbsp;</div>
                    <% } %>
                    <div>متراژ: <%= house.getArea() %> متر</div>
                    <div>نوع: <%= house.getBuildingType() %></div>
                    <% String imageUrl = house.getImageUrl(); %>
                    <div><img class="house_image" src='<%= imageUrl %>'></div>
                    <div class="linkbox">لینک عکس: <a href='<%= imageUrl %>' target="_blank"><%= imageUrl %></a></div>
                    <a href='houseDetails?houseId=<%= house.getId() %>&ownerId=<%= house.getOwner().getId() %>'><button>اطلاعات بیشتر</button></a>
                </div>
            <% } %>
        <% } else { %>
            <div class="outbox">نتیجه‌ای پیدا نشد.</div>
        <% } %>
        <jsp:include page="footer.jsp" />
    </body>
</html>
