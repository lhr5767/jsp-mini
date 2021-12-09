<%@ page import="model.BoardDao" %>
<%@ page import="java.io.*" %>
<%@ page import="java.nio.charset.StandardCharsets" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-12-06
  Time: 오후 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>

<body>
<%
    request.setCharacterEncoding("utf-8");
    String boardID = request.getParameter("boardID");

    if(boardID ==null || boardID.equals("")) {
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","접근 할 수 없습니다.");
        response.sendRedirect("index.jsp");
        return;
    }

    String root = request.getSession().getServletContext().getRealPath("/");
    String savePath = root + "upload";
    String fileName = "";
    String realFile = "";

    BoardDao boardDao = new BoardDao();
    fileName = boardDao.getFile(boardID);
    realFile = boardDao.getRealFile(boardID);

    if(fileName ==null || fileName.equals("")) { //정상적으로 파일이 업로드 안되었을때
        session.setAttribute("messageType","오류 메세지");
        session.setAttribute("messageContent","접근 할 수 없습니다.");
        response.sendRedirect("index.jsp");
        return;
    }

    InputStream in = null;
    OutputStream os = null;
    File file = null;
    boolean skip = false;
    String client = "";

    try {
        try{
            file = new File(savePath,realFile);
            in = new FileInputStream(file);

        }catch (FileNotFoundException e){
            skip = true;
        }
        client = request.getHeader("User-Agent");
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description","JSP Generated Data");
        if(!skip) {
            if(client.indexOf("MSIE") != -1) { //IE 브라우저를 사용할경우
                response.setHeader("Content-Disposition","attachment; filename=" + new String(fileName.getBytes("KSC5601"),"ISO8859_1"));
            }else {
                fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
                response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            }
            response.setHeader("Content-Length",""+file.length());
            os=response.getOutputStream();
            //버퍼를 만들어서 전송
            byte b[] = new byte[(int)file.length()];
            int leng = 0;
            while ((leng = in.read(b)) > 0) {
                os.write(b,0,leng);
            }
        }else {
            response.setContentType("text/html;charset=utf-8");
            out.println("<script>alert('파일을 찾을 수 없습니다.');history.go(-1);</script>");
        }
        in.close();
        os.close();
    }catch (Exception e) {
        e.printStackTrace();
    }
%>

</body>
</html>
