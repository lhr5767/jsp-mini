<%@ page import="java.util.ArrayList" %>

<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-11
  Time: 오후 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>강의평가 웹 사이트</title>
    <link rel="stylesheet" href="./css1/bootstrap.min.css">
    <link rel="stylesheet" href="./css1/custom.css">
</head>
<body>

<c:if test="${msg!=null}">
    <script type="text/javascript">
        alert("비밀번호가 틀렸습니다");
    </script>
</c:if>

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
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="background-color: #eeeeee;text-align: center;">번호</th>
                <th style="background-color: #eeeeee;text-align: center;">제목</th>
                <th style="background-color: #eeeeee;text-align: center;">작성자</th>
                <th style="background-color: #eeeeee;text-align: center;">작성일</th>
            </tr>
            </thead>

            <tbody>
            <c:set var="number" value="${number}"></c:set>
            <c:forEach var="bean" items="${list}">
            <tr>
                <td>${number}</td> <!--bnum은 pk로만 사용하고 목록에선 따로 번호노출 시킴-->
                <td><a style="text-decoration: none; color: black" href="ViewCon?bnum=${bean.bnum}">${bean.title}</a></td>
                <td>${bean.userid}</td>
                <td>${bean.date}</td>
            </tr>
                <c:set var="number" value="${number-1}"></c:set>
            </c:forEach>

            </tbody>
        </table>
        <p>
            <c:if test="${count>0}">
                <c:set var="pageCount" value="${count / pageSize +(count%pageSize == 0 ? 0: 1)}"></c:set>
                <c:set var="startPage" value="${1}"></c:set>

                <c:if test="${currentPage%10 != 0}">

                    <!-- 결과를 정수형으로 받아야 해서 fmt 사용 -->
                    <fmt:parseNumber var="result" value="${currentPage / 10}" integerOnly="true"/>

                    <c:set var="startPage" value="${result*10+1}"/>


                </c:if>
                <c:if test="${currentPage%10 == 0}">
                    <c:set var="startPage" value="${(result-1)*10+1}"></c:set>

                </c:if>

                <c:set var="pageBlock" value="${10}"></c:set>
                <c:set var="endPage" value="${startPage+pageBlock-1}"></c:set>

                <c:if test="${endPage>pageCount}">
                    <c:set var="endPage" value="${pageCount}"></c:set>
                </c:if>

                <!-- 이전 링크 걸어줄지 파악 -->
                <c:if test="${startPage >10}">
                    <a href="BoardListCon?pageNum=${startPage-10}">[이전]</a>
                </c:if>

                <!-- 페이징 처리 -->
                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                    <a href="BoardListCon?pageNum=${i}">[${i}]</a>
                </c:forEach>

                <!-- 다음 -->
                <c:if test="${endPage<pageCount}">
                    <a href="BoardListCon?pageNum=${startPage+10}">[다음]</a>
                </c:if>
            </c:if>


            <a href="boardWriteForm.jsp" class="btn btn-primary" >글쓰기</a>

        </p>
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
