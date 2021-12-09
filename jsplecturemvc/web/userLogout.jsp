<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-11-23
  Time: 오전 3:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate(); //모든 세션정보 파기
%>
<script>
    location.href='index.jsp';
</script>