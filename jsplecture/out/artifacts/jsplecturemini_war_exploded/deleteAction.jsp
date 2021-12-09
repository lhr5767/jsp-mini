<%@ page import="java.io.PrintWriter" %>
<%@ page import="model.EvaluationDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-10
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>


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
    //userid 비교해서 해당 사용자가 맞는경우에만 삭제하도록
    if(userid.equals(evaluationDAO.getUserID(evaluationID))){
       int result =  evaluationDAO.delete(evaluationID);

       if(result == 1) {
           PrintWriter script = response.getWriter();
           script.println("<script>");
           script.println("alert('삭제가 완료되었습니다')");
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
    }else  {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('자신이 작성한 글만 삭제 할 수 있습니다')");
        script.println("history.go(-1)");
        script.println("</script>");
        script.close();
    }

%>
