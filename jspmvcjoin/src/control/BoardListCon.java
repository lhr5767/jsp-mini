package control;

import model.BoardBean;
import model.BoardDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Vector;

@WebServlet(name = "BoardListCon", value = "/BoardListCon")
public class BoardListCon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //화면에 보여질 게시글의 개수 지정
        int pageSize=10;
        //현재 보여지는 페이지의 넘버값 읽음
        String pageNum = request.getParameter("pageNum");
        //처음엔 pageNum이 null 이므로
        if(pageNum==null) {
            pageNum="1";
        }
        //전체 게시글의 개수
        int count = 0;
        //jsp페이지 내에서 보여질 넘버링 숫자값 저장
        int number = 0;

        //현재 보여지는 페이지 문자를 숫자로 변환
        int currentPage = Integer.parseInt(pageNum);

        //db연결해서 전체 게시글 개수 가져오기
        BoardDAO boardDAO = new BoardDAO();
        count = boardDAO.getAllCount();

        //현재 보여질 페이지 시작 번호 설정
        int startRow = (currentPage -1)*pageSize+1;
        int endRow = currentPage * pageSize;

        //최신글 10개 기준으로 게시글 리턴
        Vector<BoardBean> v = boardDAO.getAllBoard(startRow,endRow);

        number = count - (currentPage -1)*pageSize;

        //수정,삭제시 비밀번호가 틀리다면
        String msg =(String) request.getAttribute("msg");

        //boardlist.jsp 로 데이터 넘기기
        request.setAttribute("v",v);
        request.setAttribute("number",number);
        request.setAttribute("pageSize",pageSize);
        request.setAttribute("count",count);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("msg",msg);

        RequestDispatcher dispatcher = request.getRequestDispatcher("boardlist.jsp");
        dispatcher.forward(request,response);

    }
}
