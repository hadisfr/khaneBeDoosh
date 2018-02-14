<%@ page contentType="text/html; charset=UTF-8" %>
<% if(request.getAttribute("msg") != null) { %>
<div dir="rtl" class="outbox msgbox">
    <%= request.getAttribute("msg") %>
</div>
<% } %>
<div dir="rtl" class="outbox">
    <div style="width: 49%; display: inline-block;">نام کاربری: <%= request.getAttribute("username") %></div>
    <div style="width: 49%; display: inline-block;">اعتبار: <%= request.getAttribute("balance") %> تومان</div>
</div>