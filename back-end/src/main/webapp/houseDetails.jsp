<%@ page contentType="text/html; charset=UTF-8" %>
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
        <%
            House house = (House) request.getAttribute("house");
        %>
        <jsp:include page="haeder.jsp" />
        <div class="outbox">
            <% if(house.getDealType() == DealType.RENT) { %>
                <div>قیمت پایه: <%= ((HouseRent)house).getBasePrice() %> تومان</div>
                <div>قیمیت اجاره: <%= ((HouseRent)house).getRentPrice() %> تومان</div>
            <% } else if(house.getDealType() == DealType.SELL) { %>
                <div>قیمت خرید: <%= ((HouseSell)house).getSellPrice() %></div>
                <div>&nbsp;</div>
            <% } %>
            <div>متراژ: <%= house.getArea() %> متر</div>
            <div>نوع: <%= house.getBuildingType() %></div>
            <div>آدرس: <%= house.getAddress() %></div>
            <div>توضیحات: <%= house.getDescription() %></div>
            <% String imageUrl = house.getImageUrl(); %>
            <div><img class="house_image" src='<%= imageUrl %>'></div>
            <div class="linkbox">لینک عکس: <a href='<%= imageUrl %>' target="_blank"><%= imageUrl %></a></div>
            <% if(request.getAttribute("wantsToSeePhone") != null && (boolean)(request.getAttribute("wantsToSeePhone"))) { %>
                <% if(request.getAttribute("canSeePhone") != null && (boolean)(request.getAttribute("canSeePhone"))) { %>
                    <div>شمارهٔ‌مالک / مشاور: <%= house.getPhone() %></div>
                <% } else { %>
                    <div>اعتبار شما کافی نیست.</div>
                <% } %>
            <% } else { %>
                <a href='getPhone?houseId=<%= house.getId() %>&ownerId=<%= house.getOwner().getId() %>'><button>دریافت شمارهٔ مالک / مشاور</button></a>
            <% } %>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
