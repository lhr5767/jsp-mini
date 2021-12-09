package control;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import model.BoardBean;
import model.BoardDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/BoardUpdateCon")
public class BoardUpdateCon extends HttpServlet {


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
            response.sendRedirect("index.jsp");
            return;
        }



        String userID = multi.getParameter("userID");

        HttpSession session = request.getSession();

        //다른 사용자가 접근 하지 못하도록
        if(!userID.equals((String)session.getAttribute("userID"))) {
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","접근 할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }

        String boardID = multi.getParameter("boardID");
        if(boardID == null || boardID.equals("")){
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","접근 할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }
        BoardDao boardDao = new BoardDao();



        //넘어온 데이터 받기

        String boardTitle = multi.getParameter("boardTitle");
        String boardContent = multi.getParameter("boardContent");

        //데이터 잘 넘어왔는지 확인
        if(boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")){
            session.setAttribute("messageType","오류 메세지");
            session.setAttribute("messageContent","모든 항목을 입력해주세요.");
            response.sendRedirect("BoardWrite.jsp");
            return;
        }

        BoardBean bean = new BoardBean();

        String boardFile ="";
        String boardRealFile = "";

        File file = multi.getFile("boardFile");
        if(file != null) { //파일이 새롭게 등록이 되었을떄
            boardFile = multi.getOriginalFileName("boardFile");
            boardRealFile = file.getName();

            String prev = boardDao.getRealFile(boardID);
            File prevFile = new File(savePath +"/" +prev);
            if(prevFile.exists()) {
                prevFile.delete(); //기존 파일을 삭제해줌
            }
        }else  { //새롭게 등록 안되었을때 기존파일 그대로사용
            boardFile = boardDao.getFile(boardID);
            boardRealFile = boardDao.getRealFile(boardID);
        }
        bean.setBoardID(Integer.parseInt(boardID));
        bean.setUserID(userID);
        bean.setBoardTitle(boardTitle);
        bean.setBoardContent(boardContent);
        bean.setBoardFile(boardFile);
        bean.setBoardRealFile(boardRealFile);



        boardDao.update(bean);

        //메세지 띄워주고 index.jsp로 이동
        request.getSession().setAttribute("messageType","성공 메세지");
        request.getSession().setAttribute("messageContent","정상적으로 게시글이 수정 되었습니다.");
        response.sendRedirect("BoardView.jsp");
    }
}
