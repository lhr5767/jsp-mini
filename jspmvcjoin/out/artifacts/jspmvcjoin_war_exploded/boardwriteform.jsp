<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-23
  Time: 오후 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
<html>

<body>
<%
    request.setCharacterEncoding("euc-kr");
%>

<h2> 게시글 쓰기</h2>

<form action="BoardWriteProcCon" method="post">
    <table width="600" border="1" bordercolor="gray">
        <tr height="40">
            <td align="center" width="150"> 작성자 </td>
            <td width="450"><input type="text" name="writer" size="60"></td>
        </tr>
        <tr height="40">
            <td align="center" width="150"> 제목 </td>
            <td width="450"><input type="text" name="subject" size="60"></td>
        </tr>
        <tr height="40">
            <td align="center" width="150"> 이메일 </td>
            <td width="450"><input type="email" name="email" size="60"></td>
        </tr>
        <tr height="40">
            <td align="center" width="150"> 비밀번호 </td>
            <td width="450"><input type="password" name="password" size="60"></td>
        </tr>
        <tr height="40">
            <td align="center" width="150"> 글 내용 </td>
            <td width="450"><textarea name="content" cols="60" rows="10"></textarea></td>
        </tr>
        <tr height="40">
            <td align="center" colspan="2">
                <input type="submit" value="글쓰기"> &nbsp;&nbsp;
                <input type="reset" value="다시작성"> &nbsp;&nbsp;
                <button onclick="location.href='BoardListCon'">전체 게시글보기</button>
            </td>
        </tr>

    </table>

</form>

</body>
</html>
