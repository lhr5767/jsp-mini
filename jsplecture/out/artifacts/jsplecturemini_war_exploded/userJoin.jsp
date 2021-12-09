<%@ page import="java.io.PrintWriter" %>
<%@ page import="model.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-09
  Time: 오후 7:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>강의평가 웹 사이트</title>
    <link rel="stylesheet" href="./css1/bootstrap.min.css">
    <link rel="stylesheet" href="./css1/custom.css">
</head>
<body>
<%
    String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }
    if(userid != null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인 된 상태입니다');");
        script.println("location.href='index.jsp'");
        script.println("</script>");
        script.close();
    }
%>
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
    <form action="userRegisterAction.jsp" method="post">
        <div class="form-group">
            <label>아이디</label>
            <input required type="text" name="userID" class="form-control">
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input required type="password" name="userPass" class="form-control">
        </div>
        <div class="form-group">
            <label>이메일</label>
            <input required type="email" name="userEmail" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">회원가입</button>
    </form>


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
