<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-06
  Time: 오후 5:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="EUC-KR" %>
<html>
<body>


<h2>답변글 작성하기</h2>

<form action="BoardReWriteProcCon" method="post">
    <table width="600" border="1">
        <tr height="40">
            <td width="150" align="center"> 작성자 </td>
            <td width="450"><input type="text" name="writer" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> 제목 </td>
            <td width="450"><input type="text" name="subject" value="[답변]" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> 이메일 </td>
            <td width="450"><input type="email" name="email" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> 비밀번호 </td>
            <td width="450"><input type="password" name="password" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> 글내용 </td>
            <td width="450"><textarea name="content" cols="50" rows="10"></textarea></td>
        </tr>

        <!-- form에서 사용자로부터 입력받지않고 데이터 넘기기 -->
        <tr height="40">
            <td align="center" colspan="2">
                <input type="hidden" name="ref" value="${ref}">
                <input type="hidden" name="re_step" value="${re_step}">
                <input type="hidden" name="re_level" value="${re_level}">
                <input type="submit" value="답글저장하기"> &nbsp;&nbsp;
                <input type="reset" value="취소"> &nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardListCon'" value="전체글보기">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
