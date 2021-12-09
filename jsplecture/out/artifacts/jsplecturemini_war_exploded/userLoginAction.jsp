<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-10
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ page import ="model.UserBean"%>
<%@ page import ="model.UserDAO"%>
<%@ page import ="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
    request.setCharacterEncoding("utf-8");
    String userid = null;
    String userpassword = null;
    //넘어온 데이터 받기
    if(request.getParameter("userID") !=null) {
        userid = request.getParameter("userID");
    }
    if(request.getParameter("userPass")!=null) {
        userpassword = request.getParameter("userPass");
    }

    //데이터가 정상적으로 넘어오지 않은경우
    if(userid ==null || userpassword==null){
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('입력이 안된 사항이 있습니다');");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();

    }

    UserDAO userDAO = new UserDAO();

    int result = userDAO.login(userid,userpassword);
    if(result == 1) {
        session.setAttribute("userid",userid);
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("location.href='index.jsp'");
        script.println("</script>");
        script.close();

    }else if(result ==0) { //비밀번호 틀린경우
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('비밀번호가 틀립니다')");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();

    }else if(result == -1) {//아이디 없는 경우
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('존재하지않는 아이디 입니다')");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();

    }
%>
