<%@ page import="model.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-30
  Time: 오후 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%
        String userID = null;
        if(session.getAttribute("userID")!=null) {
            userID = (String) session.getAttribute("userID");
        }
        String toID = null;
        if(request.getParameter("toID")!=null) {
            toID = request.getParameter("toID");
        }

        if(userID == null) {
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","현재 로그인이 되어있지 않습니다");
            response.sendRedirect("index.jsp");
            return;
        }
        if(toID == null) {
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","대화 상대가 지정되지 않았습니다");
            response.sendRedirect("index.jsp");
            return;
        }
        if(userID.equals(toID)) {
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","자기 자신에게는 메세지를 보낼 수 없습니다");
            response.sendRedirect("index.jsp");
            return;
        }
        UserDao userDao = new UserDao();
        //자신의 프로필 사진
        String fromProfile = userDao.getProfile(userID);
        //상대방의 프로필 사진
        String toProfile = userDao.getProfile(toID);
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>JSP 실시간 회원제 채팅 서비스</title>
    <link rel="stylesheet" href="./css1/bootstrap.css">
    <link rel="stylesheet" href="./css1/custom.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script type="text/javascript">
        function autoClosingAlert(selector, delay) {
            var alert = $(selector).alert();
            alert.show();
            window.setTimeout(function() { alert.hide() } ,delay);
        }
        function submitFunction() {
            var fromID = '<%=userID%>';
            var toID = '<%=toID%>';
            var chatContent =$('#chatContent').val();
            $.ajax({
                type : "post",
                url: "/ChatSubmitCon",
                data: {
                    fromID: fromID,
                    toID: toID,
                    chatContent: chatContent,
                },
                success: function (result) { //데이터가 ChatSubmitCon으로 정상 전송 됐을때 result에 결과값 저장
                    if(result == 1) {
                        autoClosingAlert("#successMessage",2000); //2초동안 메시지 보여줌
                    }else if(result == 0){
                        autoClosingAlert("#dangerMessage",2000);
                    }else {
                        autoClosingAlert("#warningMessage",2000);
                    }
                }
            });
            $('#chatContent').val(''); //전송후 chatContent 를 비워줌
        }
        var lastID = 0;
        function chatListFunction(type) {
            var fromID = '<%=userID%>';
            var toID = '<%=toID%>';
            $.ajax({
                type: "post",
                url: "/ChatListCon",
                data: {
                    fromID: fromID,
                    toID: toID,
                    listType: type
                },
                success: function (data) {
                    if(data == "") return;
                    var parsed = JSON.parse(data); //data를 json형태로 파싱
                    var result = parsed.result;
                    for(var i=0; i< result.length;i ++) {
                        if(result[i][0].value == fromID){
                            result[i][0].value = '나'; //메세지를 보낸사람이 자기자신 이면 '나' 라고 나오도록
                        }
                        addChat(result[i][0].value,result[i][2].value,result[i][3].value)
                    }
                    lastID = Number(parsed.last); //가장 마지막에 전달받은 chatid 받아옴
                }
            });
        }
        function addChat(chatName, chatContent, chatTime){
            if(chatName == '나') {
                $('#chatList').append('<div class="row">' +
                    '<div class="col-lg-12">' +
                    '<div class="media">' +
                    '<a class="pull-left" href="#">' +
                    '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="<%= fromProfile %>" alt="">' +
                    '</a>' +
                    '<div class="media-body">' +
                    '<h4 class="media-heading">' +
                    chatName +
                    '<span class="small pull-right">' +
                    chatTime +
                    '</span>' +
                    '</h4>' +
                    '<p>' +
                    chatContent +
                    '</p>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<hr>');
            }else  {
                $('#chatList').append('<div class="row">' +
                    '<div class="col-lg-12">' +
                    '<div class="media">' +
                    '<a class="pull-left" href="#">' +
                    '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="<%= toProfile %>" alt="">' +
                    '</a>' +
                    '<div class="media-body">' +
                    '<h4 class="media-heading">' +
                    chatName +
                    '<span class="small pull-right">' +
                    chatTime +
                    '</span>' +
                    '</h4>' +
                    '<p>' +
                    chatContent +
                    '</p>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<hr>');
            }
            $('#chatList').scrollTop($('#chatList')[0].scrollHeight); //메세지 올때마다 스크롤을 내려줌
        }
        function getInfiniteChat() {
            setInterval(function () {
                chatListFunction(lastID);
            }, 3000); //3초에 한번씩 chatListFunction 실행시킴
        }

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
            <li><a href="BoardView.jsp">자유게시판</a></li>

        </ul>
        <%
            if(userID != null) {
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
<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet portlet-default">
                <div class="portlet-heading">
                    <div class="portlet-title">
                        <h4> <i class="fa fa-circle text-green"></i> 실시간 채팅창</h4>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div id="chat" class="panel-collapse collapse in">
                    <div id="chatList" class="portlet-body chat-widget" style="overflow-y:auto;width: auto;height: 600px; ">

                    </div>
                    <div class="portlet-footer">
                        <div class="row" style="height: 90px;">
                            <div class="form-group col-xs-10">
                                <textarea style="height: 80px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요" maxlength="100" ></textarea>
                            </div>
                            <div class="form-group col-xs-2">
                                <button type="button" class="btn btn-default pull-right" onclick="submitFunction();">전송</button>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="alert alert-success" id="successMessage" style="display: none">
    <strong>메세지 전송에 성공했습니다.</strong>
</div>
<div class="alert alert-danger" id="dangerMessage" style="display: none">
    <strong>이름과 내용을 모두 입력하세요.</strong>
</div>
<div class="alert alert-warning" id="warningMessage" style="display: none">
    <strong>데이터베이스 오류가 발생했습니다.</strong>
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
<script type="text/javascript">
    $(document).ready(function () {
        getUnread();
        chatListFunction('0');
        getInfiniteChat(); //3초 간격으로 메세지 가져오도록
        getInfiniteUnread();

    });
</script>

</body>
</html>
