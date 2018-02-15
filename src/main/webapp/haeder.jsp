<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="main.java.KhaneBeDoosh" %>
<% if(request.getAttribute("msg") != null) { %>
<div dir="rtl" class="outbox msgbox">
    <%= request.getAttribute("msg") %>
</div>
<% } %>
<div dir="rtl" class="outbox">
    <div style="width: 6%; display: inline-block;"><a href="."><img class="logo" src='<%= KhaneBeDoosh.logoUri %>'></a></div>
    <div style="width: 46%; display: inline-block;">نام کاربری: <%= request.getAttribute("username") %></div>
    <div style="width: 46%; display: inline-block;">اعتبار: <%= request.getAttribute("balance") %> تومان</div>
</div>