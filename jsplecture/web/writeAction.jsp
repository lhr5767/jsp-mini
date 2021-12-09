<%@ page import="java.io.PrintWriter" %>
<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-11
  Time: 오후 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    request.setCharacterEncoding("euc-kr");
    String title = request.getParameter("title");
    String content = request.getParameter("content");


    String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }
    if(userid==null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요')");
        script.println("location.href='userLogin.jsp'");
        script.println("</script>");
        script.close();
    }else {


            BoardDAO boardDAO = new BoardDAO();
            boardDAO.write(title,userid,content);
            response.sendRedirect("boardList.jsp");
        }


%>

</body>
</html>
