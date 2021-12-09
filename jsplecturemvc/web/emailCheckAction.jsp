<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-10
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ page import ="model.UserBean"%>
<%@ page import ="model.UserDao"%>
<%@ page import ="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
  request.setCharacterEncoding("utf-8");
  String code = null;
  UserDao userDAO = new UserDao();
  String userid = null;

  if(request.getParameter("code") != null){
    code = request.getParameter("code");
  }

  if(session.getAttribute("userid") != null){
    userid = (String) session.getAttribute("userid");
  }
  if(userid == null){
    PrintWriter script = response.getWriter();
    script.println("<script>");
    script.println("alert('로그인을 해주세요');");
    script.println("location.href='userLogin.jsp'");
    script.println("</script>");
    script.close();
  }

  String userEmail = userDAO.getUserEmail(userid);
  //넘어온 code와 사용자의 이메일에 해쉬 적용한 값을 비교
  boolean isRight = (SHA256.getSHA256(userEmail).equals(code))? true:false;

  if(isRight == true) {
    userDAO.setUserEmailChecked(userid);
    PrintWriter script = response.getWriter();
    script.println("<script>");
    script.println("alert('인증에 성공했습니다');");
    script.println("location.href='index.jsp'");
    script.println("</script>");
    script.close();
  } else {
    PrintWriter script = response.getWriter();
    script.println("<script>");
    script.println("alert('유효하지 않은 코드입니다다);");
    script.println("location.href='index.jsp'");
    script.println("</script>");
    script.close();
  }


%>
