<%@ page contentType="text/html; charset=UTF-8" %>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
    body {
        background: #eee;
        font-family: Yekan, Tahoma, Sans-Serif;
        direction: rtl;
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
        width: 32%;
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
</style>

<%
    request.setAttribute("username", request.getParameter("username"));
    if(request.getAttribute("username") == null)
        request.setAttribute("username", "بهنام همایون");
    request.setAttribute("balance", request.getParameter("balance"));
    if(request.getAttribute("balance") == null)
        request.setAttribute("balance", 200);
%>