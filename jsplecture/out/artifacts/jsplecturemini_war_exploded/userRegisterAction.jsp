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

<% String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }
    if(userid != null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인된 상태입니다');");
        script.println("location.href='index.jsp'");
        script.println("</script>");
        script.close();
    }
    request.setCharacterEncoding("utf-8");
    String userpassword = null;
    String useremail = null;
    //넘어온 데이터 받기

        userid = request.getParameter("userID");


        userpassword = request.getParameter("userPass");


        useremail = request.getParameter("userEmail");



    UserDAO userDAO = new UserDAO();
    UserBean bean = new UserBean();
    bean.setUserid(userid);
    bean.setUserpassword(userpassword);
    bean.setUseremail(useremail);
    bean.setUseremailhash(SHA256.getSHA256(useremail));
    bean.setUseremailhaschecked(false);

    int result = userDAO.join(bean);
    if(result == 0) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 존재하는 아이디 입니다');");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();

    }else { //회원가입 완료시 바로 이메일 인증하도록
        session.setAttribute("userid",userid);
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("location.href='emailSendAction.jsp';");
        script.println("</script>");
        script.close();

    }
%>
