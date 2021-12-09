package control;

import model.BoardBean;
import model.BoardDao;
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

@WebServlet("/BoardDeleteCon")
public class BoardDeleteCon extends HttpServlet {



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
        response.setContentType("text/html;charset=utf-8");

        //userID는 세션에 저장된 값으로 가져옴
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        String boardID = request.getParameter("boardID");
        String userPassword = request.getParameter("userPassword");

        //데이터 정상적으로 넘어왔는지 확인
        if(boardID == null || boardID.equals("")){

            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","접근할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }

        BoardDao boardDao = new BoardDao();
        BoardBean bean = boardDao.getBoard(boardID);

        //로그인한 유저가 작성한 글이 아닌경우 삭제하지 못하도록
        if(userID.equals(bean.getUserID())==false) {
            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","접근할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;

        }

        String save ="/upload";
        ServletContext context = getServletContext();
        String realfolder = context.getRealPath(save);

        String prev = boardDao.getRealFile(boardID);

        //게시글 삭제
        boardDao.delete(boardID);
        //게시글 삭제후 파일업로드 된 것이 존재하면 삭제
        File prevFile = new File(realfolder+"/"+prev);
        if(prevFile.exists()){
            prevFile.delete();
            request.getSession().setAttribute("messageType","성공 메세지");
            request.getSession().setAttribute("messageContent","성공적으로 삭제 되었습니다.");
            response.sendRedirect("BoardView.jsp");
            return;
        }
    }
}
