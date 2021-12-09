<%@ page import="java.io.PrintWriter" %>
<%@ page import="model.EvaluationDAO" %>
<%@ page import="model.LikeyDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-10
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%!
    //ip구하는 메서드
    public static String getIP(HttpServletRequest request){
        String ip = request.getHeader("X-ForWARDED-FOR");
        if(ip == null || ip.length()==0){
            ip= request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length()==0){
            ip= request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length()==0){
            ip= request.getRemoteAddr();
        }
        return ip;
    }
%>

<%

    String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }

    if(userid == null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요.')");
        script.println("location.href='userLogin.jsp'");
        script.println("</script>");
        script.close();
    }
    String evaluationID = null;
    if(request.getParameter("evaluationID") != null){
        evaluationID = request.getParameter("evaluationID");
    }
    EvaluationDAO evaluationDAO = new EvaluationDAO();
    LikeyDAO likeyDAO = new LikeyDAO();

    int result = likeyDAO.like(userid,evaluationID,getIP(request));

    if(result ==1) {
        result = evaluationDAO.like(evaluationID);
        if(result == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('추천이 완료되었습니다.');");
            script.println("location.href='index.jsp'");
            script.println("</script>");
            script.close();
        }else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('데이터베이스 오류입니다')");
            script.println("history.go(-1)");
            script.println("</script>");
            script.close();
        }
    }else {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 추천을 했습니다')");
        script.println("history.go(-1)");
        script.println("</script>");
        script.close();
    }



%>
