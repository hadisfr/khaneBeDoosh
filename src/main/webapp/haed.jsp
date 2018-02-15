<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="main.java.KhaneBeDoosh" %>
<%@ page import="main.java.User" %>
<%@ page import="main.java.Individual" %>

<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
    body {
        background: #eee;
        font-family: Yekan, Tahoma, Sans-Serif;
        direction: rtl;
        text-align: center;
    }
    div.outbox {
        padding: 16px;
        text-align: center;
        background: #fff;
        border-radius: 16px;
        margin: 16px;
        border: 2px solid #ccc;
    }
    div.msgbox {
        background: DarkRed;
        border: none;
        color: white;
    }
    div.grid_element {
        width: 27%;
        display: inline-block;
    }
    form {
        text-align: center;
    }
    button, input[type="text"], fieldset {
        margin: 6px;
        height: 30px;
        display: inline-block;
        width: 45%;
    }
    input[type="text"], fieldset {
        width: 45%;
    }
    fieldset {
        border: none;
    }
    div.linkbox {
        white-space: nowrap;
        overflow-x: hidden;
        font-size: smaller;
    }
    img.house_image {
        max-width: 100%; 
        height: 200px;
        border-radius: 16px;
    }
    div.footer {
        text-align: center;
        font-size: small;
        color: #999;
        direction: ltr;
    }
</style>

<%
    if(request.getAttribute("user") == null)
        request.setAttribute("user", KhaneBeDoosh.getInstance().getDefaultUser());
    Individual currentUser = (Individual)(request.getAttribute("user"));
    request.setAttribute("username", currentUser.getName());
    request.setAttribute("balance", currentUser.getBalance());
%>