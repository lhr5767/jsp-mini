<%@ page import="java.lang.reflect.Parameter" %>
<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-13
  Time: 오전 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<%
    int bnum = Integer.parseInt(request.getParameter("bnum"));
    BoardDAO boardDAO = new BoardDAO();

    boardDAO.delete(bnum);

    response.sendRedirect("boardList.jsp");
%>

</body>
</html>
