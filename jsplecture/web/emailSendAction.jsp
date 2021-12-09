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
<%@ page import="javax.mail.*" %>
<%@ page import="java.util.Properties" %>
<%@ page import="util.Gmail" %>
<%@ page import="javax.mail.internet.MimeMessage" %>
<%@ page import="javax.mail.internet.InternetAddress" %>
<%@ page import="javax.mail.Transport" %>
<%@ page import="javax.mail.Message" %>
<%@ page import="javax.mail.Address" %>
<%@ page import="javax.mail.Authenticator" %>
<%@ page import="javax.mail.Session" %>

<%
    UserDAO userDAO = new UserDAO();
    String userid = null;
    if(session.getAttribute("userid") !=null){
        userid = (String) session.getAttribute("userid");
    }
    if(userid == null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요');");
        script.println("location.href='userLogin.jsp';");
        script.println("</script>");
        script.close();
    }



    boolean emailChecked = userDAO.getUserEmailChecked(userid);
    if(emailChecked == true) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 인증된 회원입니다');");
        script.println("location.href='index.jsp'");
        script.println("</script>");
        script.close();

    }
    String host = "http://localhost:8080/";
    String from = "관리자 이메일";
    String to = userDAO.getUserEmail(userid);
    String subject = "강의평가를 위한 이메일 인증 메일입니다";
    String content = "인증을 위해 다음 링크에 접속하세요" +
            "<a href='"+host+"emailCheckAction.jsp?code="+SHA256.getSHA256(to)+"'>이메일 인증하기</a>";

    //이메일 보내기 위한 설정들
    Properties p = new Properties();
    p.put("mail.smtp.user",from);
    p.put("mail.smtp.host","smtp.googlemail.com");
    p.put("mail.smtp.port","465");
    p.put("mail.smtp.starttls.enable","true");
    p.put("mail.smtp.auth","true");
    p.put("mail.smtp.debug","true");
    p.put("mail.smtp.socketFactory.port","465");
    p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    p.put("mail.smtp.socketFactory.fallback","false");

    try {
        Authenticator auth = new Gmail();
        Session ses = Session.getInstance(p,auth);
        ses.setDebug(true);
        MimeMessage msg = new MimeMessage(ses);
        //메일 제목
        msg.setSubject(subject);
        //보내는 사람 정보
        Address fromAddr = new InternetAddress(from);
        msg.setFrom(fromAddr);
        //받는사람 정보
        Address toAddr = new InternetAddress(to);
        msg.addRecipient(Message.RecipientType.TO,toAddr);
        //메일 내용
        msg.setContent(content,"text/html;charset=UTF-8");
        Transport.send(msg);

    }catch (Exception e){
        e.printStackTrace();
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('오류가 발생했습니다');");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();
    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>강의평가 웹 사이트</title>
    <link rel="stylesheet" href="./css1/bootstrap.min.css">
    <link rel="stylesheet" href="./css1/custom.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="index.jsp" class="navbar-brand">강의평가 웹 사이트</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbar" >
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a href="index.jsp" class="nav-link">메인</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    회원관리
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <%
                        if(userid == null) {
                    %>

                    <a href="userLogin.jsp" class="dropdown-item">로그인</a>
                    <a href="userJoin.jsp" class="dropdown-item">회원가입</a>
                    <%
                    }else{
                    %>
                    <a href="userLogout.jsp" class="dropdown-item">로그아웃</a>
                    <%
                        }
                    %>
                </div>
            </li>
        </ul>
        <form action="index.jsp" method="get" class="form-inline my-2 my-lg-0">
            <input type="search" name="search" class="form-control mr-sm-2" placeholder="내용을 입력하세요">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
        </form>
    </div>
</nav>

<section class="container mt-4" style="max-width: 560px;">
    <div class="alert alert-success mt-4" role="alert">
        인증 메일이 전송되었습니다. 회원가입시 입력한 이메일을 확인해주세요
    </div>


</section>

<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
    Copyright &copy; 2021 LHR all rights reserved
</footer>


<!-- 제이쿼리 자바스크립트 추가-->
<script src="./js1/jquery.min.js"></script>
<!-- 파퍼 자바스크립트 추가-->
<script src="./js1/popper.js"></script>
<!-- 부트스트랩 자바스크립트 추가-->
<script src="./js1/bootstrap.min.js"></script>
</body>
</html>

