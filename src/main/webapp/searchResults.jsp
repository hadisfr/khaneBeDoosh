<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="haed.jsp" />
    </head>
    <body>
        <jsp:include page="haeder.jsp" />
        <% for (House house : request.getAttribute("houses")) { %>
            <div class="outbox grid_element">
                <%-- rent --%>
                قیمت پایه: ** تومان
                قیمیت اجاره: ** تومان
                <%-- bye --%>
                قیمت خرید: **
                متراژ: ** متر
                نوع: **
                لینک عکس: **
                <a href="javascript: void(0);">اطلاعات بیشتر</a>
            </div>
        <% } %>
    </body>
</html>
