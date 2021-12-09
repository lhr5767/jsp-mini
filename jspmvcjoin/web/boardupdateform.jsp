<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-06
  Time: 오후 6:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<body>

<h2>게시글 수정</h2>

<form action="BoardUpdateProcCon" method="post">
    <table width="600" border="1">
        <tr height="40">
            <td width="120" align="center"> 작성자 </td>
            <td width="180" align="center">${bean.writer}</td>
            <td width="120" align="center">작성일</td>
            <td width="180" align="center">${bean.reg_date}</td>
        </tr>
        <tr height="40">
            <td width="120" align="center">제목</td>
            <td width="480" colspan="3"> &nbsp; <input size="60" type="text" name="subject" value="${bean.subject}"></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">패스워드</td>
            <td width="480" colspan="3"> &nbsp; <input size="60" type="password" name="password"></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">글 내용</td>
            <td width="480" colspan="3"><textarea name="content" cols="60" rows="10" align="left"> ${bean.content}</textarea></td>
        </tr>
        <tr height="40">
            <td colspan="4" align="center">
                <input type="hidden" name="num" value="${bean.num}">
                <input type="hidden" name="pass" value="${bean.password}">
                <input type="submit" value="글 수정"> &nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardListCon'" value="전체글보기">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
