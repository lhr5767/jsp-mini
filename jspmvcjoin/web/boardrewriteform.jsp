<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-06
  Time: ���� 5:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="EUC-KR" %>
<html>
<body>


<h2>�亯�� �ۼ��ϱ�</h2>

<form action="BoardReWriteProcCon" method="post">
    <table width="600" border="1">
        <tr height="40">
            <td width="150" align="center"> �ۼ��� </td>
            <td width="450"><input type="text" name="writer" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> ���� </td>
            <td width="450"><input type="text" name="subject" value="[�亯]" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> �̸��� </td>
            <td width="450"><input type="email" name="email" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> ��й�ȣ </td>
            <td width="450"><input type="password" name="password" size="60"></td>
        </tr>
        <tr height="40">
            <td width="150" align="center"> �۳��� </td>
            <td width="450"><textarea name="content" cols="50" rows="10"></textarea></td>
        </tr>

        <!-- form���� ����ڷκ��� �Է¹����ʰ� ������ �ѱ�� -->
        <tr height="40">
            <td align="center" colspan="2">
                <input type="hidden" name="ref" value="${ref}">
                <input type="hidden" name="re_step" value="${re_step}">
                <input type="hidden" name="re_level" value="${re_level}">
                <input type="submit" value="��������ϱ�"> &nbsp;&nbsp;
                <input type="reset" value="���"> &nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardListCon'" value="��ü�ۺ���">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
