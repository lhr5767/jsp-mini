package control;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import model.UserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/UserProfileUpdateCon")
public class UserProfileUpdateCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }
    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        MultipartRequest multi = null;


        //파일의 최대크기
        int fileMaxSize = 10*1024*1024;

        String savePath = request.getSession().getServletContext().getRealPath("/upload").replaceAll("\\\\","/");

        String save ="/upload";
        ServletContext context = getServletContext();
        String realfolder = context.getRealPath(save);
        try{
            multi = new MultipartRequest(request,realfolder,fileMaxSize,"UTF-8",new DefaultFileRenamePolicy());
        }catch (Exception e){
            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","파일 크기는 10MB를 넘을수 없습니다.");
            response.sendRedirect("ProfileUpdate.jsp");
            return;
        }

        System.out.println(multi);
        String userID = multi.getParameter("userID");

        HttpSession session = request.getSession();

        //다른 사용자가 접근 하지 못하도록
        if(!userID.equals((String)session.getAttribute("userID"))) {
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","접근 할 수 없습니다.");
            response.sendRedirect("index.jsp");
        }

        String fileName = "";
        File file = multi.getFile("userProfile");
        if(file != null) {
            //파일의 확장자 확인하기
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            //해당 확장자만 가진 파일 업로드 가능 하도록
            if(ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
                String prev = new UserDao().getUser(userID).getUserProfile();
                File prevFile = new File(realfolder +"/" + prev);
                //기존의 프로필은 지워주기
                if(prevFile.exists()) {
                    prevFile.delete();
                }
                fileName = file.getName();
            } else {
                //이미지 파일 외 다른것을 업로드 하였을때 file을 지워줌
               if(file.exists()) {
                    file.delete();
                }
                session.setAttribute("messageType","오류 메세지");
                session.setAttribute("messageContent","이미지 파일만 업로드 가능합니다.");
                response.sendRedirect("ProfileUpdate.jsp");
            }
        }

        //이후 db에 변경된 프로필 변경
        new UserDao().profile(userID,fileName);

        //메세지 띄워주고 index.jsp로 이동
        request.getSession().setAttribute("messageType","성공 메세지");
        request.getSession().setAttribute("messageContent","프로필이 정상적으로 수정 되었습니다.");
        response.sendRedirect("index.jsp");
    }
}
