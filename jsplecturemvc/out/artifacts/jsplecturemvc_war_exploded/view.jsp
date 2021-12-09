
<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-12
  Time: 오전 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a href="boardList.jsp" class="nav-link">게시판</a>
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

        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th colspan="3" style="background-color: #eeeeee;text-align: center;">게시판 글보기</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%; ">글제목</td>
                <td colspan="2">${bean.title}</td>
            </tr>
            <tr>
                <td>작성자</td>
                <td colspan="2">${bean.userid}</td>
            </tr>
            <tr>
                <td>작성일</td>
                <td colspan="2">${bean.date}</td>
            </tr>
            <tr>
                <td>내용</td>
                <!-- 특수문자 정상 출력 되도록 replaceAll 로 처리 해주어야함-->
                <td colspan="2" height="200">${bean.content}</td>
            </tr>
            </tbody>
        </table>
        <p>
            <a href="BoardListCon" class="btn btn-primary">목록</a>

            <c:if test="${userid ne null && bean.userid.equals(userid)}">

            <a href="BoardUpdateCon?bnum=${bean.bnum}" class="btn btn-primary">수정</a>
            <a onclick="return confirm('정말 삭제하시겠습니까?')" href="BoardDeleteCon?bnum=${bean.bnum}" class="btn btn-primary">삭제</a>
            </c:if>
        </p>
    </div>
</div>


<!-- 제이쿼리 자바스크립트 추가-->
<script src="./js1/jquery.min.js"></script>
<!-- 파퍼 자바스크립트 추가-->
<script src="./js1/popper.js"></script>
<!-- 부트스트랩 자바스크립트 추가-->
<script src="./js1/bootstrap.min.js"></script>

</body>
</html>
