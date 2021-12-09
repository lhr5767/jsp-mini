<%@ page import="model.BoardBean" %>
<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-12
  Time: 오전 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
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
    request.setCharacterEncoding("euc-kr");
    String userid = null;
    if(session.getAttribute("userid") != null) {
        userid = (String) session.getAttribute("userid");
    }

    int bnum = Integer.parseInt(request.getParameter("bnum"));
    BoardBean bean = new BoardBean();
    BoardDAO boardDAO = new BoardDAO();

    bean = boardDAO.getOneBoard(bnum);
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
            <li class="nav-item active">
                <a href="boardList.jsp" class="nav-link">게시판</a>
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
    </div>
</nav>


<div class="container">
    <div class="row">
        <form method="post" action="updateAction.jsp?bnum=<%=bnum%>">
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th colspan="3" style="background-color: #eeeeee;text-align: center;">글 수정하기</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%; ">글제목</td>
                <td colspan="2"><input required type="text" name="title" value="<%=bean.getTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>")%>"></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td colspan="2"><%=bean.getUserid()%></td>
            </tr>
            <tr>
                <td>작성일</td>
                <td colspan="2"><%=bean.getDate()%></td>
            </tr>
            <tr>
                <td>내용</td>
                <!-- 특수문자 정상 출력 되도록 replaceAll 로 처리 해주어야함-->
                <td colspan="2" height="200"><textarea required name="content"  maxlength="2000"><%=bean.getContent()%></textarea></td>
            </tr>
            </tbody>
        </table>
            <input type="submit" class="btn btn-primary" value="글수정">
        </form>
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
