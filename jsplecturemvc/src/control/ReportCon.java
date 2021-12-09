package control;

import model.UserDao;
import util.Gmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet("/ReportCon")
public class ReportCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        UserDao userDao = new UserDao();
        String userid = null;

        if(session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        if(userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요');");
            script.println("location.href='userLogin.jsp';");
            script.println("</script>");
            script.close();
        }

        String reportTitle = null;
        String reportContent = null;

        if(request.getParameter("reportTitle") != null){
            reportContent = request.getParameter("reportTitle");
        }

        if(request.getParameter("reportContent")!=null) {
            reportContent = request.getParameter("reportContent");
        }



        String host = "http://localhost:8080/";
        String from = userDao.getUserEmail(userid);
        String to = "lhr5767@gmail.com";
        String subject = "강의평가 사이트에서 접수된 신고 메일입니다.";
        String content = "신고자 :" +userid +
                "<br>제목: " + reportTitle +
                "<br>내용: " +reportContent ;

        //이메일 보내기 위한 설정들
        Properties p = new Properties();
        p.put("mail.smtp.user",from);
        p.put("mail.smtp.host","smtp.googlemail.com");
        p.put("mail.smtp.port","465");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.debug","true");
        p.put("mail.smtp.socketFactory.port","465");
        p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.socketFactory.fallback","false");

        try{
            Authenticator auth = new Gmail();
            Session ses = Session.getInstance(p,auth);
            ses.setDebug(true);
            MimeMessage msg = new MimeMessage(ses);

            //메일 제목
            msg.setSubject(subject);
            //보내는 사람 정보
            Address frmoAddr = new InternetAddress(from);
            msg.setFrom(frmoAddr);

            //받는사람 정보
            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO,toAddr);

            //메일 내용
            msg.setContent(content,"text/html;charset=UTF-8");
            Transport.send(msg);
        }catch (Exception e) {
            e.printStackTrace();
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('오류가 발생했습니다');");
            script.println("history.go(-1);");
            script.println("</script>");
            script.close();
        }

        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('정상적으로 신고 되었습니다');");
        script.println("history.go(-1);");
        script.println("</script>");
        script.close();

    }
}
