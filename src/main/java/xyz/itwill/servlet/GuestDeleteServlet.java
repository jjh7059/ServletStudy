package xyz.itwill.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.itwill.dao.GuestDAO;

//출력페이지(select.itwill)에서 전달된 방명록 게시글번호를 반환받아 GUEST 테이블에 저장된
//해당 글번호의 방명록 게시글을 삭제하고 출력페이지(select.itwill)를 요청하여 이동하는 정보를
//전달하는 서블릿 - 처리페이지(삭제)
@WebServlet("/delete.itwill")
public class GuestDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//비정상적인 요청에 대한 처리
		if(request.getParameter("no") == null) {//전달값이 없는 경우
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		//전달값을 반환받아 저장 - 전달받은 문자열을 정수값으로 변환하여 저장
		int no = Integer.parseInt(request.getParameter("no"));
		
		//GUEST 테이블에 저장된 방명록 게시글 중 전달받은 글번호의 게시글을 삭제하는
		//DAO 클래스의 메소드 호출
		int rows = GuestDAO.getDAO().deleteGuest(no);
		
		if(rows > 0) {//삭제된 행이 있는 경우
			response.sendRedirect("select.itwill");
		} else {//삭제된 행이 없는 경우 - 비정상적인 요청
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

}
