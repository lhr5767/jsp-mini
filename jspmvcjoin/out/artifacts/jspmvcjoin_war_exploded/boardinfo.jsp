<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-06
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<h2>게시글 보기</h2>
<table width="600" border="1" >
    <tr height="40">
        <td align="center" width="120"> 글번호 </td>
        <td align="center" width="180"> ${bean.num}</td>
        <td align="center" width="120"> 조회수 </td>
        <td align="center" width="180"> ${bean.readcount}</td>
    </tr>
    <tr height="40">
        <td align="center" width="120">작성자</td>
        <td align="center" width="180">${bean.writer}</td>
        <td align="center" width="120"> 작성일</td>
        <td align="center" width="180">${bean.reg_date}</td>
    </tr>
    <tr height="40">
        <td align="center" width="120">이메일</td>
        <td align="center" colspan="3">${bean.email}</td>
    </tr>
    <tr height="40">
        <td align="center" width="120">제목</td>
        <td align="center" colspan="3">${bean.subject}</td>
    </tr>
    <tr height="40">
        <td align="center" width="120">글 내용</td>
        <td align="center" colspan="3">${bean.content}</td>
    </tr>
    <tr height="40">
        <td align="center" colspan="4">
            <input type="button" value="답글쓰기" onclick="location.href='BoardReWriteCon?num=${bean.num}&ref=${bean.ref}&re_step=${bean.re_step}&re_level=${bean.re_level}'">
            <input type="button" value="수정하기" onclick="location.href='BoardUpdateCon?num=${bean.num}'">
            <input type="button" value="삭제하기" onclick="location.href='BoardDeleteCon?num=${bean.num}'">
            <input type="button" value="목록보기" onclick="location.href='BoardListCon'">
        </td>
    </tr>
</table>
</body>
</html>
