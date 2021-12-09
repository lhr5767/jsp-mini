<%@ page import="model.UserBean" %>
<%@ page import="model.UserDao" %><%--
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
    UserBean bean = new UserBean();
    UserDao userDao = new UserDao();

    bean = userDao.getUser(userID);
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
        function passwordCheckFunction() {
            var userPassword1 = $('#userPassword1').val();
            var userPassword2 = $('#userPassword2').val();
            if(userPassword1 != userPassword2){
                $('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다');
            }else {
                $('#passwordCheckMessage').html('');
            }

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

        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false" role="button">
                    회원관리<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="Update.jsp">정보수정</a></li>
                    <li class="active"><a href="ProfileUpdate.jsp">프로필</a></li>
                    <li><a href="Logout.jsp">로그아웃</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <form action="BoardWriteCon" method="post" enctype="multipart/form-data" >
        <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th colspan="3"><h4>게시글 작성 양식</h4></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 110px;"><h5>아이디</h5></td>
                <td colspan="2"><h5><%=bean.getUserID()%></h5>
                    <input type="hidden" name="userID" value="<%=bean.getUserID()%>">
                </td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>글 제목</h5></td>
                <td colspan="2"><input class="form-control" type="text" name="boardTitle" placeholder="글 제목을 입력하세요" maxlength="50"></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>글 내용</h5></td>
                <td colspan="2"><textarea class="form-control" name="boardContent" id="" placeholder="내용을 입력하세요" maxlength="2048"></textarea></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>파일 업로드</h5></td>
                <td colspan="2">
                    <input type="file" name="boardFile" class="file">
                    <div class="input-group col-xs-12">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
                        <input type="text" class="form-control input-lg" disabled placeholder="파일을 업로드하세요">
                        <span class="input-group-btn">
                        <button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i>파일찾기</button>
                    </span>
                    </div>

                </td>
            </tr>

            <tr>
                <td style="text-align: left" colspan="3"><h5 style="color: red"></h5><input
                        type="submit" class="btn btn-primary pull-right" value="등록" ></td>
            </tr>
            </tbody>
        </table>
    </form>
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

<script type="text/javascript">
    $(document).on('click','.browse',function () {
        var file = $(this).parent().parent().parent().find('.file');
        file.trigger('click');
    });
    $(document).on('change','.file',function () {
        $(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i,''));
    })
</script>


</body>
</html>
