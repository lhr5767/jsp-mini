<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-10
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="model.EvaluationDAO" %>

<%
    request.setCharacterEncoding("euc-kr");
    String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }
    if(userid == null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요');");
        script.println("location.href='userLogin.jsp'");
        script.println("</script>");
        script.close();
    }
%>
<jsp:useBean id="evalbean" class="model.EvaluationBean">
    <jsp:setProperty name="evalbean" property="*"/>
</jsp:useBean>

<%
    evalbean.setUserid(userid);
    EvaluationDAO evaluationDAO = new EvaluationDAO();

    evaluationDAO.write(evalbean);

    response.sendRedirect("index.jsp");
%>
