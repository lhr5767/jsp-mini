<%@ page import="java.net.URLEncoder" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-22
  Time: 오후 8:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
      <form action="EvaluationListCon" method="get" class="form-inline my-2 my-lg-0">
        <input type="text" name="search" class="form-control mr-sm-2" placeholder="내용을 입력하세요">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
      </form>
    </div>
  </nav>

  <section class="container">
    <form action="EvaluationListCon" method="get" class="form-inline mt-3">
      <select name="lecturedivide" class="form-control mx-1 mt-2">
        <option value="전체">전체</option>
        <option value="전공" >전공</option>
        <option value="교양" >교양</option>
        <option value="기타" >기타</option>
      </select>
      <select name="searchtype" class="form-control mx-1 mt-2">
        <option value="최신순">최신순</option>
        <option value="추천순">추천순</option>

      </select>
      <c:if test="${userid ne null}">
        <input type="text" name="search" class="form-control mx-1 mt-2" value="${search}" placeholder="내용을 입력하세요">
        <button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
        <a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal"> 등록하기</a>
        <a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal"> 신고</a>
      </c:if>

      <c:if test="${userid eq null}">
        <input type="text" name="search" class="form-control mx-1 mt-2" value="" placeholder="내용을 입력하세요">
        <button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
        <a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal"> 신고</a>
      </c:if>

    </form>

    <c:if test="${list ne null}">
    <c:forEach var="bean" items="${list}" varStatus="status">

    <div class="card bg-light mt-3">
      <div class="card-header bg-light">
        <div class="row">
          <div class="col-8 text-left">${bean.lecturename}&nbsp;<small>${bean.professorname}</small>
          </div>
          <div class="col-4 text-right">
            종합 <span style="collapse: red;">${bean.totalscore}</span>
          </div>
        </div>
      </div>
      <div class="card-body">
        <h5 class="card-title">${bean.title}&nbsp;<small>${bean.lectureyear} 년 ${bean.semesterdivide}</small></h5>
        <p class="card-text">${bean.content}</p>
        <div class="row">
          <div class="col-9 text-left">
            성적 <span style="color: red;">${bean.creditscore}</span>
            분위기 <span style="color: red;">${bean.feelscore}</span>
            강의 <span style="color: red;">${bean.lecturescore}</span>
            <span style="color: green;">(추천:)${bean.likecount}</span>
          </div>
          <div class="col-3 text-right">
            <a onclick="return confirm('추천하시겠습니까?')" href="LikeCon?evaluationID=${bean.evaluationID}">추천</a>
            <a onclick="return confirm('삭제하시겠습니까?')" href="DeleteCon?evaluationID=${bean.evaluationID}">삭제</a>
          </div>
        </div>
      </div>
    </div>
    </c:forEach>
    </c:if>
  </section>
  <ul class="pagination justify-content-center mt-3">
    <li class="page-item">

      <c:if test="${pageNumber<=0}">
      <a class="page-link disabled">이전</a>
      </c:if>

      <c:if test="${pageNumber > 0}">
      <a href="EvaluationListCon?" class="page-link" >
        이전
      </a>
      </c:if>

    </li>
    <li>

      <c:if test="${bean.size() < 6}">
      <a class="page-link disabled">다음</a>
      </c:if>
      <c:if test="${bean.size() >= 6}">
      <a href="#" class="page-link" >
        다음
      </a>
      </c:if>
    </li>
  </ul>
  <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modal">평가 등록</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true"> &times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="evaluationRegisterCon" method="post">
            <div class="form-row">
              <div class="form-group col-sm-6">
                <label> 강의명 </label>
                <input type="text" name="lecturename" class="form-control" maxlength="20">
              </div>
              <div class="form-group col-sm-6">
                <label> 교수명 </label>
                <input type="text" name="professorname" class="form-control" maxlength="20">
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-sm-4">
                <label>수강연도</label>
                <select name="lectureyear" class="form-control">
                  <option value="2016">2016</option>
                  <option value="2017">2017</option>
                  <option value="2018">2018</option>
                  <option value="2019">2019</option>
                  <option value="2020">2020</option>
                  <option value="2021" selected>2021</option>
                  <option value="2022">2022</option>
                  <option value="2023">2023</option>
                </select>

              </div>
              <div class="form-group col-sm-4">
                <label>수강 학기</label>
                <select name="semesterdivide" class="form-control">
                  <option value="1학기" selected>1학기</option>
                  <option value="여름학기">여름학기</option>
                  <option value="2학기">2학기</option>
                  <option value="겨울학기">겨울학기</option>
                </select>
              </div>
              <div class="form-group col-sm-4">
                <label>강의 구분</label>
                <select name="lecturedivide" class="form-control">
                  <option value="전공" selected>전공</option>
                  <option value="교양">교양</option>
                  <option value="기타">기타</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label>제목</label>
              <input type="text" name="title" class="form-control" maxlength="30">
            </div>
            <div class="form-group">
              <label>내용</label>
              <textarea name="content" class="form-control" maxlength="2000" style="height: 180px;"></textarea>
            </div>
            <div class="form-row">
              <div class="form-group col-sm-3">
                <label >종합</label>
                <select name="totalscore" class="form-control">
                  <option value="A" selected>A</option>
                  <option value="B">B</option>
                  <option value="C">C</option>
                  <option value="D">D</option>
                  <option value="F">F</option>
                </select>
              </div>
              <div class="form-group col-sm-3">
                <label >성적</label>
                <select name="creditscore" class="form-control">
                  <option value="A" selected>A</option>
                  <option value="B">B</option>
                  <option value="C">C</option>
                  <option value="D">D</option>
                  <option value="F">F</option>
                </select>
              </div>
              <div class="form-group col-sm-3">
                <label >분위기</label>
                <select name="feelscore" class="form-control">
                  <option value="A" selected>A</option>
                  <option value="B">B</option>
                  <option value="C">C</option>
                  <option value="D">D</option>
                  <option value="F">F</option>
                </select>
              </div>
              <div class="form-group col-sm-3">
                <label >강의</label>
                <select name="lecturescore" class="form-control">
                  <option value="A" selected>A</option>
                  <option value="B">B</option>
                  <option value="C">C</option>
                  <option value="D">D</option>
                  <option value="F">F</option>
                </select>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
              <button type="submit" class="btn btn-primary">등록하기</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modal">신고 하기</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true"> &times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="ReportCon" method="post">

            <div class="form-group">
              <label>신고 제목</label>
              <input type="text" name="reportTitle" class="form-control" maxlength="30">
            </div>
            <div class="form-group">
              <label>신고 내용</label>
              <textarea name="reportContent" class="form-control" maxlength="2000" style="height: 180px;"></textarea>
            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
              <button type="submit" class="btn btn-danger">신고하기</button>
            </div>
          </form>

        </div>
      </div>
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
