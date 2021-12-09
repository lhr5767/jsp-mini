package control;

import model.MemberBean;
import model.MemberDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/proc.do")
public class MemberJoinProc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        request.setCharacterEncoding("euc-kr");

        MemberBean bean = new MemberBean();
        bean.setId(request.getParameter("id"));

        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");

        bean.setPass1(pass1);
        bean.setPass2(pass2);

        bean.setEmail(request.getParameter("email"));
        bean.setTel(request.getParameter("tel"));

        String[] arr = request.getParameterValues("hobby");
        String data = "";
        for(String string : arr) {
            data += string+" ";
        }
        bean.setHobby(data);
        bean.setJob(request.getParameter("job"));
        bean.setAge(request.getParameter("age"));
        bean.setInfo(request.getParameter("info"));

        //패스워드 같은 경우에만 db에 저장
        if(pass1.equals(pass2)) {
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.insertMember(bean);

            //또 다른 컨트롤러를 호출해주어야함
            RequestDispatcher dispatcher=request.getRequestDispatcher("MemberListCon.do");
            dispatcher.forward(request,response);

        }else {
            request.setAttribute("msg","패스워드가 일치하지 않습니다");
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginerror.jsp");
            dispatcher.forward(request,response);
        }


    }
}
