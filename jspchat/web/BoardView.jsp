<%@ page import="model.BoardBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.BoardDao" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-30
  Time: 오후 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%
    String userID = null;
    if(session.getAttribute("userID")!=null) {
        userID = (String) session.getAttribute("userID");
    }

    //로그인 안하면 접근 못하도록
   if(userID == null) {
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","현재 로그인이 되어있지 않습니다");
        response.sendRedirect("index.jsp");
        return;
    }
    //pageNumber 값 설정
    String pageNumber = "1";
    if(request.getParameter("pageNumber") != null) {
        pageNumber = request.getParameter("pageNumber");
    }
    try{
        Integer.parseInt(pageNumber);
    }catch (Exception e) {
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","페이지 번호가 잘못되었습니다.");
        response.sendRedirect("BoardView.jsp");
        return;
    }

    ArrayList<BoardBean> list = new ArrayList<>();
    BoardDao boardDao = new BoardDao();

    list = boardDao.getList(pageNumber);

    System.out.println(list.size());
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>JSP 실시간 회원제 채팅 서비스</title>
    <link rel="stylesheet" href="./css1/bootstrap.css">
    <link rel="stylesheet" href="./css1/custom.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script type="text/javascript">
        function getUnread() {
            $.ajax({
                type:'post',
                url: '/ChatUnreadCon',
                data:{
                    userID : '<%=userID%>',
                },
                success: function (result) {
                    if(result >= 1) {
                        showUnread(result);
                    }else {
                        showUnread('');
                    }
                }
            });
        }
        function getInfiniteUnread() { //4초마다 getUnread() 호출
            setInterval(function () {
                getUnread();
            }, 4000);
        }
        function showUnread(result) {
            $('#unread').html(result);
        }

    </script>
</head>
<body>


<nav class="navbar navbar-default">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="index.jsp" class="navbar-brand">실시간 회원제 채팅 서비스</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="index.jsp">메인</a></li>
            <li><a href="Find.jsp">친구찾기</a></li>
            <li><a href="Box.jsp">메세지함<span id="unread" class="label label-info"></span></a></li>
            <li class="active"><a href="BoardView.jsp">자유게시판</a></li>
        </ul>
        <%
            if(userID == null) {
        %>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false" role="button">
                    접속하기<span class="caret"></span>

                </a>
                <ul class="dropdown-menu">
                    <li><a href="Login.jsp">로그인</a></li>
                    <li><a href="Join.jsp">회원가입</a></li>
                </ul>
            </li>
        </ul>
        <%
        } else {
        %>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false" role="button">
                    회원관리<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="Update.jsp">정보수정</a></li>
                    <li><a href="ProfileUpdate.jsp">프로필</a></li>
                    <li><a href="Logout.jsp">로그아웃</a></li>
                </ul>
            </li>
        </ul>
        <%
            }
        %>
    </div>
</nav>

<div class="container">
    <table class="table table-bordered table-hover" style="text-align: center;border: 1px solid #dddddd">
        <thead>
            <tr>
                <th colspan="5">자유게시판</th>
            </tr>
            <tr>
                <th style="background-color: #fafafa; color: #000000;width: 70px;"><h5>번호</h5></th>
                <th style="background-color: #fafafa; color: #000000;"><h5>제목</h5></th>
                <th style="background-color: #fafafa; color: #000000;"><h5>작성자</h5></th>
                <th style="background-color: #fafafa; color: #000000; width: 100px;"><h5>작성일</h5></th>
                <th style="background-color: #fafafa; color: #000000; width: 70px;"><h5>조회수</h5></th>
            </tr>
        </thead>

        <tbody>
        <%
            for(int i= 0 ; i< list.size(); i++) {
                BoardBean bean = list.get(i);
        %>
            <tr>
                <td><%=bean.getBoardID()%></td>
                <td style="text-align: left">
                    <a href="BoardInfo.jsp?boardID=<%=bean.getBoardID()%>">
        <%
            for(int j = 0 ; j < bean.getBoardLevel();j ++) { //답변글 들여쓰기 하기 위한 부분
        %>
                        <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
                <%
                }
                %>
                <%
                    if(bean.getBoardAvailable() == 0) {
                %>
                        (삭제된 게시물 입니다.)
                <%
                    }else {
                %>
                        <%=bean.getBoardTitle()%>
                <%
                    }
                %>
                </a></td>
                <td><%=bean.getUserID()%></td>
                <td><%=bean.getBoardDate()%></td>
                <td><%=bean.getBoardHit()%></td>
            </tr>
        <%
            }
        %>
            <tr>
                <td colspan="5">
                    <a href="BoardWrite.jsp" class="btn btn-primary pull-right" type="submit">글쓰기</a>
                    <ul class="pagination" style="margin: 0 auto;">
                   <%
                       int startPage = (Integer.parseInt(pageNumber) / 10) * 10 +1;
                       if(Integer.parseInt(pageNumber) % 10 ==0) startPage -= 10;
                       int targetPage = boardDao.targetPage(pageNumber);
                       if(startPage != 1) {

                   %>
                        <li><a href="BoardView.jsp?pageNumber=<%=startPage - 1%>"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
                    <%
                        }else {
                    %>
                        <li><span class="glyphicon glyphicon-chevron-left" style="color: gray"></span></li>
                    <%
                        }
                       for(int i = startPage; i < Integer.parseInt(pageNumber); i++) {
                    %>
                        <li><a href="BoardView.jsp?pageNumber=<%=i%>"><%=i%></a></li>

                   <%
                        }
                    %>
                        <li class="active"><a href="BoardView.jsp?pageNumber=<%=pageNumber%>"><%=pageNumber%></a></li>
                    <%
                        for(int i = Integer.parseInt(pageNumber) +1; i<=targetPage + Integer.parseInt(pageNumber); i++) {
                            if(i < startPage + 10) {
                    %>
                        <li><a href="BoardView.jsp?pageNumber=<%=i%>"><%=i%></a></li>

                    <%
                            }
                        }
                        if(targetPage + Integer.parseInt(pageNumber) > startPage + 9) {
                    %>
                        <li><a href="BoardView.jsp?pageNumber=<%=startPage+10%>"><span class="glyphicon-chevron-right"></span></a></li>

                    <%
                        } else {
                    %>
                        <li><span class="glyphicon glyphicon-chevron-right" style="color: gray"></span></li>
                    <%
                        }
                    %>
                    </ul>
                </td>
            </tr>
        </tbody>
    </table>
</div>


<%
    String messageContent = null;
    if(session.getAttribute("messageContent") != null) {
        messageContent = (String) session.getAttribute("messageContent");
    }
    String messageType = null;
    if(session.getAttribute("messageType") != null) {
        messageType = (String) session.getAttribute("messageType");
    }

    if(messageContent != null){
%>
<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="vertical-alignment-helper">
        <div class="modal-dialog vertical-align-center">
            <div class="modal-content <%if(messageType.equals("오류 메세지")) out.println("panel-warning"); else out.println("panel-success"); %>">
                <div class="modal-header panel-heading">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">
                        <%=messageType%>
                    </h4>
                </div>
                <div class="modal-body">
                    <%=messageContent%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    $('#messageModal').modal('show');
</script>


<%
        session.removeAttribute("messageContent");
        session.removeAttribute("messageType");
    }
%>


<%
    if(userID != null){ //로그인한 상태라면
%>
<script type="text/javascript">
    $(document).ready(function () {
        getUnread();
        getInfiniteUnread();
    });
</script>

<%
    }
%>


</body>
</html>
