<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-30
  Time: 오후 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>JSP 실시간 회원제 채팅 서비스</title>
    <link rel="stylesheet" href="./css1/bootstrap.css">
    <link rel="stylesheet" href="./css1/custom.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script type="text/javascript">
        function registerCheckFunction() {
            var userID = $('#userID').val();
            $.ajax({
                type: 'POST',
                url: '/UserRegisterCheckCon',
                data: {userID : userID},
                success : function (result) {
                    if(result == 1) {
                        $('#checkMessage').html('사용 가능한 아이디입니다');
                        $('#checkType').attr('class','modal-content panel-success');
                    }else {
                        $('#checkMessage').html('사용할 수 없는 아이디입니다');
                        $('#checkType').attr('class','modal-content panel-success');
                    }
                    $('#checkModal').modal('show');
                }
            });
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

<%
    String userID = null;
    if(session.getAttribute("userID")!=null) {
        userID = (String) session.getAttribute("userID");
    }
    if(userID != null) {
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","현재 로그인 되어있는 상태입니다");
        response.sendRedirect("index.jsp");
        return;
    }
%>

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
                    <li class="active"><a href="Join.jsp">회원가입</a></li>
                </ul>
            </li>
        </ul>
        <%
            }
        %>
    </div>
</nav>

<div class="container">
    <form action="UserRegisterCon" method="post">
        <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th colspan="3"><h4>회원 등록 양식</h4></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 110px;"><h5>아이디</h5></td>
                <td><input class="form-control" type="text" id="userID" name="userID" maxlength="20" placeholder="아이디를 입력하세요"></td>
                <td style="width: 110px;"><button class="btn btn-primary" onclick="registerCheckFunction();" type="button">중복체크</button></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>비밀번호</h5></td>
                <td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword1" name="userPassword1" maxlength="20" placeholder="비밀번호를 입력하세요"></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>비밀번호 확인</h5></td>
                <td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword2" name="userPassword2" maxlength="20" placeholder="비밀번호를 입력하세요"></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>이름</h5></td>
                <td colspan="2"><input class="form-control" type="text" name="userName" maxlength="20" placeholder="이름을 입력하세요"></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>나이</h5></td>
                <td colspan="2"><input class="form-control" type="number" name="userAge" maxlength="20" placeholder="나이를 입력하세요"></td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>성별</h5></td>
                <td colspan="2">
                    <div class="form-group" style="text-align: center;margin: 0 auto;">
                        <div class="btn-group" data-toggle="buttons">
                            <label class="btn btn-primary active">
                                <input type="radio" name="userGender" autocomplete="off" value="남자"checked>남자
                            </label>
                            <label class="btn btn-primary">
                                <input type="radio" name="userGender" autocomplete="off" value="여자">여자
                            </label>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td style="width: 110px;"><h5>이메일</h5></td>
                <td colspan="2"><input class="form-control" type="email" name="userEmail" maxlength="50" placeholder="이메일을 입력하세요"></td>
            </tr>
            <tr>
                <td style="text-align: left" colspan="3"><h5 style="color: red" id="passwordCheckMessage"></h5><input
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
</body>
</html>
