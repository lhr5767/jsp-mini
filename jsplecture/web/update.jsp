<%@ page import="model.BoardBean" %>
<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-12
  Time: ���� 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>������ �� ����Ʈ</title>
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
    <a href="index.jsp" class="navbar-brand">������ �� ����Ʈ</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbar" >
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a href="index.jsp" class="nav-link">����</a>
            </li>
            <li class="nav-item active">
                <a href="boardList.jsp" class="nav-link">�Խ���</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    ȸ������
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <%
                        if(userid == null) {
                    %>

                    <a href="userLogin.jsp" class="dropdown-item">�α���</a>
                    <a href="userJoin.jsp" class="dropdown-item">ȸ������</a>
                    <%
                    }else{
                    %>
                    <a href="userLogout.jsp" class="dropdown-item">�α׾ƿ�</a>
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
                <th colspan="3" style="background-color: #eeeeee;text-align: center;">�� �����ϱ�</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%; ">������</td>
                <td colspan="2"><input required type="text" name="title" value="<%=bean.getTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>")%>"></td>
            </tr>
            <tr>
                <td>�ۼ���</td>
                <td colspan="2"><%=bean.getUserid()%></td>
            </tr>
            <tr>
                <td>�ۼ���</td>
                <td colspan="2"><%=bean.getDate()%></td>
            </tr>
            <tr>
                <td>����</td>
                <!-- Ư������ ���� ��� �ǵ��� replaceAll �� ó�� ���־����-->
                <td colspan="2" height="200"><textarea required name="content"  maxlength="2000"><%=bean.getContent()%></textarea></td>
            </tr>
            </tbody>
        </table>
            <input type="submit" class="btn btn-primary" value="�ۼ���">
        </form>
    </div>
</div>


<!-- �������� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/jquery.min.js"></script>
<!-- ���� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/popper.js"></script>
<!-- ��Ʈ��Ʈ�� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/bootstrap.min.js"></script>

</body>
</html>
