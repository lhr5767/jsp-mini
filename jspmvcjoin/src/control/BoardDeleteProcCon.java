package control;

import model.BoardBean;
import model.BoardDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BoardDeleteProcCon")
public class BoardDeleteProcCon extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //넘어온 데이터 받기
        int num = Integer.parseInt(request.getParameter("num"));
        String password = request.getParameter("password");
        String pass = request.getParameter("pass");

        //패스워드 같을때만 삭제하고 리스트화면 보여주기
        if(pass.equals(password)) {
            BoardDAO boardDAO = new BoardDAO();
            boardDAO.deleteBoard(num);

            RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
            dispatcher.forward(request,response);
        }else {
            request.setAttribute("msg","2");
            RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
            dispatcher.forward(request,response);
        }
    }

}
