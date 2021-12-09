<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-13
  Time: 오전 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    int bnum = Integer.parseInt(request.getParameter("bnum"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    BoardDAO boardDAO = new BoardDAO();

    boardDAO.update(bnum,title,content);


    response.sendRedirect("boardList.jsp");

%>

</body>
</html>
