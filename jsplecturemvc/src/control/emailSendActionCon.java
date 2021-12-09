package control;

import model.UserDao;
import util.Gmail;
import util.SHA256;

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

@WebServlet("/emailSendActionCon")
public class emailSendActionCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        UserDao userDao = new UserDao();

        HttpSession session = request.getSession();

        String userid = null;

        if(session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        boolean emailChecked = userDao.getUserEmailChecked(userid);
        if(emailChecked == true) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('이미 인증된 회원입니다');");
            script.println("location.href='index.jsp'");
            script.println("</script>");
            script.close();

        }
        String host = "http://localhost:8080/";
        String from = "관리자 이메일";
        String to = userDao.getUserEmail(userid);
        String subject = "강의평가를 위한 이메일 인증 메일입니다";
        String content = "인증을 위해 다음 링크에 접속하세요" +
                "<a href='"+host+"emailCheckAction.jsp?code="+ SHA256.getSHA256(to)+"'>이메일 인증하기</a>";


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


        try {
            Authenticator auth = new Gmail();
            Session ses = Session.getInstance(p,auth);
            ses.setDebug(true);
            MimeMessage msg = new MimeMessage(ses);
            //메일 제목
            msg.setSubject(subject);
            //보내는 사람 정보
            Address fromAddr = new InternetAddress(from);
            msg.setFrom(fromAddr);
            //받는사람 정보
            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO,toAddr);
            //메일 내용
            msg.setContent(content,"text/html;charset=UTF-8");
            Transport.send(msg);

            response.sendRedirect("emailSendAction.jsp");

        }catch (Exception e){
            e.printStackTrace();
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('오류가 발생했습니다');");
            script.println("history.go(-1);");
            script.println("</script>");
            script.close();
        }

    }
}
