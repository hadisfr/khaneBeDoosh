<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="main.java.House" %>
<%@ page import="main.java.DealType" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="haed.jsp" />
    </head>
    <body>
        <jsp:include page="haeder.jsp" />
        <div class="outbox">
            <%
            House house = (House)(request.getAttribute("house"));
            if(house != null) {
            %>
                <% if(house.getDealType() == DealType.RENT) { %>
                    <div>قیمت پایه: <%= house.getBasePrice() %> تومان</div>
                    <div>قیمیت اجاره: <%= house.getRentPrice() %> تومان</div>
                <% } else  { %>
                    <div>قیمت خرید: <%= house.getSellPrice() %></div>
                <% } %>
                <div>متراژ: <%= house.getArea() %> متر</div>
                <div>نوع: <%= house.getBuildingType() %></div>
                <div>آدرس: <%= house.getAddress() %></div>
                <div>توضیحات: <%= house.getDescription() %></div>
                <div>لینک عکس: <a href='<%= house.getImageUrl() %>' target="_blank"><%= house.getImageUrl() %></a></div>
                <% if((boolean)(request.getAttribute("wants_to_see_phone"))) { %>
                    <% if((boolean)(request.getAttribute("can_see_phone"))) { %>
                        <div>شمارهٔ‌مالک / کشاور: <%= house.getPhone() %></div>
                    <% } else { %>
                        <div>اعتبار شما کافی نیست.</div>
                    <% } %>
                <% } else { %>
                    <a href="javascript: void(0);"><button>دریافت شمارهٔ مالک / مشاور</button></a>
                <% } %>
            <% } else { %>
                خانه‌ای با این شناسه پیدا نشد.
            <% } %>
        </div>
    </body>
</html>
