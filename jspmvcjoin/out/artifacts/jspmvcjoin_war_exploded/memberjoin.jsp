<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-12
  Time: ���� 6:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=EUC-KR" language="java" pageEncoding="euc-kr" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>ȸ�� ����</h2>
<form action="proc.do" method="post">
    <table width="500" border="1">
        <tr height="50">
            <td width="150" align="center">���̵�</td>
            <td width="350" align="center"><input type="text" name="id" size="40"></td>
        </tr>
        <tr height="50">
            <td width="150" align="center">�н�����</td>
            <td width="350" align="center"><input type="password" name="pass1" size="40"></td>
        </tr>
        <tr height="50">
            <td width="150" align="center">�н����� Ȯ��</td>
            <td width="350" align="center"><input type="password" name="pass2" size="40"></td>
        </tr>
        <tr height="50">
            <td width="150" align="center">�̸���</td>
            <td width="350" align="center"><input type="email" name="email" size="40"></td>
        </tr>
        <tr height="50">
            <td width="150" align="center">��ȭ��ȣ</td>
            <td width="350" align="center"><input type="tel" name="tel" size="40"></td>
        </tr>
        <tr height="50">
            <td width="150" align="center">����� ���ɺо�</td>
            <td width="350" align="center">
                <input type="checkbox" name="hobby" value="ķ��">ķ�� &nbsp;&nbsp;
                <input type="checkbox" name="hobby" value="���">��� &nbsp;&nbsp;
                <input type="checkbox" name="hobby" value="��ȭ">��ȭ &nbsp;&nbsp;
                <input type="checkbox" name="hobby" value="����">���� &nbsp;&nbsp;


            </td>
        </tr>
        <tr height="50">
            <td width="150" align="center"> ����� ������</td>
            <td width="350" align="center">
                <select name="job">
                    <option value="����">����</option>
                    <option value="��ȣ��">��ȣ��</option>
                    <option value="�ǻ�">�ǻ�</option>
                    <option value="�����">�����</option>

                </select>
            </td>
        </tr>
        <tr height="50">
            <td width="150" align="center">����� ������</td>
            <td width="350" align="center">
                <input type="radio" name="age" value="10">10�� &nbsp;&nbsp;
                <input type="radio" name="age" value="20">20�� &nbsp;&nbsp;
                <input type="radio" name="age" value="30">30�� &nbsp;&nbsp;
                <input type="radio" name="age" value="40">40�� &nbsp;&nbsp;
            </td>
        </tr>
        <tr height="50">
            <td width="150" align="center">�ϰ������</td>
            <td width="350" align="center">
                <textarea name="info" cols="40" rows="5"></textarea>
            </td>
        </tr>

        <tr height="50">
            <td align="center" colspan="2">
                <input type="submit" value="ȸ�� ����">
                <input type="reset" value="���">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
