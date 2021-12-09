<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-06
  Time: 오후 7:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<h2>게시글 삭제</h2>

<form action="BoardDeleteProcCon" method="post">
    <table width="600" border="1">

        <tr height="40">
            <td width="120" align="center">패스워드</td>
            <td width="480" colspan="3"> &nbsp; <input size="60" type="password" name="password"></td>
        </tr>

        <tr height="40">
            <td colspan="4" align="center">
                <input type="hidden" name="num" value="${bean.num}">
                <input type="hidden" name="pass" value="${bean.password}">
                <input type="submit" value="글 삭제"> &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardListCon'" value="전체글보기">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
