<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-11
  Time: 오후 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    if(userid == null){
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요')");
        script.println("history.go(-1)");
        script.println("</script>");
        script.close();
    }
%>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="EvaluationListCon" class="navbar-brand">강의평가 웹 사이트</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbar" >
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a href="EvaluationListCon" class="nav-link">메인</a>
            </li>
            <li class="nav-item active">
                <a href="BoardListCon" class="nav-link">게시판</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    회원관리
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">

                    <c:if test="${ userid eq null}">
                        <a href="userLogin.jsp" class="dropdown-item">로그인</a>

                        <a href="userJoin.jsp" class="dropdown-item">회원가입</a>
                    </c:if>
                    <c:if test="${userid ne null}">
                        <a href="userLogout.jsp" class="dropdown-item">로그아웃</a>
                    </c:if>

                </div>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row">
        <form method="post" action="BoardWriteProcCon">
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                <tr>
                    <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글쓰기 양식</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="2"><input required type="text" class="form-control" placeholder="글 제목" name="title"maxlength="50" ></td>
                </tr>
                <tr>
                    <td colspan="2"><textarea required class="form-control" placeholder="글 내용" name="content" maxlength="2000" style="height: 350px;">

                </textarea>
                    </td>

                </tr>
                </tbody>
            </table>
            <input type="submit" class="btn btn-primary" value="글작성 완료">
        </form>
    </div>
</div>
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
