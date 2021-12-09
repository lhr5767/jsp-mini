<%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-12-02
  Time: 오전 2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<%
    session.invalidate(); //모든 세션 정보 파기

%>
<script>
    location.href="index.jsp"; //세션파기 후 인덱스 페이지로 이동
</script>
</body>
</html>
