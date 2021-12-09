package control;

import model.BoardDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BoardUpdateProcCon")
public class BoardUpdateProcCon extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("euc-kr");
        //form 에서 넘어온 데이터 받기
        int num = Integer.parseInt(request.getParameter("num"));
        String password = request.getParameter("password"); // 사용자로부터 입력받은 패스워드
        String pass = request.getParameter("pass"); //실제 DB에 저장된 패스워드

        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        //pass와 password 비교 같을때만 수정완료
        if(pass.equals(password)) {
            BoardDAO boardDAO = new BoardDAO();
            boardDAO.updateBoard(num,subject,content);
            //수정 완료후 전체게시글 보기로 이동
            request.setAttribute("msg","수정이 완료 되었습니다");
            RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
            dispatcher.forward(request,response);
        }else {
            //이전 페이지로 이동
            request.setAttribute("msg","비밀번호가 맞지 않습니다");
            RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
            dispatcher.forward(request,response);
        }
    }

}
