package control;

import model.MemberBean;
import model.MemberDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Vector;

@WebServlet("/MemberListCon.do")
public class MemberListCon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //db에 연결해 모든회원정보 리턴
        MemberDAO memberDAO = new MemberDAO();
        Vector<MemberBean> v = memberDAO.getAllMember();

        request.setAttribute("v",v); //v를 jsp쪽으로 넘기기

        RequestDispatcher dispatcher = request.getRequestDispatcher("memberlist.jsp");
        dispatcher.forward(request,response);
    }
}
