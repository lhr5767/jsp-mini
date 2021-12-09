<%@ page import="java.util.ArrayList" %>
<%@ page import="model.BoardBean" %>
<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-11
  Time: ���� 10:21
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

    int pageSize = 10;
    String pageNum = request.getParameter("pageNum");

    if(pageNum==null) {
        pageNum="1";
    }
    int count = 0;
    int number = 0;
    int currentPage = Integer.parseInt(pageNum);

    BoardDAO boardDAO = new BoardDAO();

    count = boardDAO.getAllCount();

    int startRow = (currentPage-1)*pageSize+1;
    int endRow = currentPage * pageSize;

    ArrayList<BoardBean> list = new ArrayList<>();
    list= boardDAO.getAllBoard(startRow,endRow);

    number =count - (currentPage-1) * pageSize;

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
        <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="background-color: #eeeeee;text-align: center;">��ȣ</th>
                <th style="background-color: #eeeeee;text-align: center;">����</th>
                <th style="background-color: #eeeeee;text-align: center;">�ۼ���</th>
                <th style="background-color: #eeeeee;text-align: center;">�ۼ���</th>
            </tr>
            </thead>
<%
    for(int i=0; i<list.size();i++) {
        BoardBean bean = list.get(i);
%>
            <tbody>
                <tr>
                    <td><%=number--%></td> <!--bnum�� pk�θ� ����ϰ� ��Ͽ��� ���� ��ȣ���� ��Ŵ-->
                    <td><a style="text-decoration: none; color: black" href="view.jsp?bnum=<%=bean.getBnum()%>"><%=bean.getTitle()%></a></td>
                    <td><%=bean.getUserid()%></td>
                    <td><%=bean.getDate()%></td>
                </tr>
<%
    }
%>
            </tbody>
        </table>
        <p>
            <%
                if(count > 0) {
                    int pageCount = count/pageSize + (count%pageSize == 0? 0:1);
                    int startPage = 1;

                    if(currentPage % 10 != 0) {
                        startPage = (int)(currentPage/10)*10+1;
                    }else {
                        startPage = ((int)(currentPage/10)-1)*10+1;
                    }

                    int pageBlock = 10;
                    int endPage = startPage+pageBlock-1;

                    if(endPage>pageCount) endPage = pageCount;

                    if(startPage > 10) {

            %>
            <a href="boardList.jsp?pageNum=<%=startPage-10%>">����</a>
            <%
                }
                    for(int i=startPage; i<=endPage;i++){
            %>
            <a class="btn btn-primary" href="boardList.jsp?pageNum=<%=i%>">[<%=i%>]</a>
            <%
                }
                    if(endPage<pageCount){
            %>
            <a href="boardList.jsp?pageNum=<%=startPage+10%>">[����]</a>
            <%
                }
                }
            %>
            <a href="write.jsp" class="btn btn-primary" >�۾���</a>

        </p>
    </div>
</div>
<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
    Copyright &copy; 2021 LHR all rights reserved
</footer>

<!-- �������� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/jquery.min.js"></script>
<!-- ���� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/popper.js"></script>
<!-- ��Ʈ��Ʈ�� �ڹٽ�ũ��Ʈ �߰�-->
<script src="./js1/bootstrap.min.js"></script>
</body>
</html>
