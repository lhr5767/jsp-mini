<%--
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
    if(userID == null) {
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","현재 로그인이 되어있지 않습니다");
        response.sendRedirect("index.jsp");
        return;
    }
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
        function findFunction() {
            var userID = $('#findID').val();
            $.ajax({
                type : 'post',
                url : '/UserFindCon',
                data: {userID : userID},
                success : function (result) {
                    if(result == -1) {
                        $('#checkMessage').html('해당 사용자를 찾을 수 없습니다.');
                        $('#checkType').attr('class','modal-content panel-warning');
                        failFriend();
                    }else {
                        $('#checkMessage').html('친구찾기에 성공 했습니다.');
                        $('#checkType').attr('class','modal-content panel-success');
                        var data = JSON.parse(result);
                        var profile = data.userProfile;
                        getFriend(userID,profile);
                    }
                    $('#checkModal').modal('show');
                }
            });
        }
        function getFriend(findID,userProfile) {
            $('#friendResult').html('<thead>' +
                    '<tr>' +
                    '<th><h4>검색 결과</h4></th>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody>' +
                    '<tr>' +
                    '<td style="text-align: center;">' +
                    '<img class="media-object img-circle" style="max-width: 300px; margin: 0 auto;" src="' + userProfile +'"/>'+

                    '<h3>' + findID +'</h3><a href="Chat.jsp?toID=' + findID + '" class="btn btn-primary pull-right">' +'메세지 보내기</a></td>' +
                    '</tr>' +
                    '</tbody>');
        }
        function failFriend() {
            $('#friendResult').html(''); //테이블의 내용을 지워줌
        }
    </script>
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
            <li class="active"><a href="Find.jsp">친구찾기</a></li>
            <li><a href="Box.jsp">메세지함<span id="unread" class="label label-info"></span></a></li>
            <li><a href="BoardView.jsp">자유게시판</a></li>

        </ul>

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

    </div>
</nav>

<div class="container">
    <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
        <thead>
        <tr>
            <th colspan="2"><h4>검색으로 친구찾기</h4></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="width: 110px; "><h5>친구 아이디</h5></td>
            <td><input class="form-control" type="text" id="findID" placeholder="찾을 아이디를 입력하세요" maxlength="20"></td>
        </tr>
        <tr>
            <td colspan="2">
                <button class="btn btn-primary pull-right" onclick="findFunction();">검색</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<div class="container">
    <table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;" >

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

<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="vertical-alignment-helper">
        <div class="modal-dialog vertical-align-center">
            <div id="checkType" class="modal-content panel-info">
                <div class="modal-header panel-heading">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">
                        확인 메세지
                    </h4>
                </div>
                <div id="checkMessage" class="modal-body">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>


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
