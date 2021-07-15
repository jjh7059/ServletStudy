package xyz.itwill.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.itwill.dao.GuestDAO;
import xyz.itwill.dto.GuestDTO;

//입력페이지(insert.html)에서 전달된 방명록 게시글을 반환받아 GUEST 테이블에 저장하고
//출력페이지(select.itwill)를 요청하여 이동하는 정보를 전달하는 서블릿 - 처리페이지
@WebServlet("/insert.itwill")
public class GuestInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//비정상적인 요청에 대한 처리
		if(request.getMethod().equals("GET")) {
			//클라이언트에게 405 상태코드 전달
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		
		//리퀘스트 메시지의 바디에 전달값(입력값)에 대한 캐릭터셋 변경
		request.setCharacterEncoding("utf-8");
		
		//전달값(입력값)을 반환받아 저장
		// => 반환받은 전달값의 앞 또는 뒤의 공백을 제거한 후 저장
		// => XSS 공격에 대한 방어를 위해 태그 관련 기호를 회피(Escape)문자로 변환하고 저장
		//XSS(Cross Site Scripting) : 입력태그의 악의적인 스크립트를 입력하여 페이지가 변형
		//되거나 개인정보를 특정 사이트로 유출하는 공격
		String name = request.getParameter("name").trim().replace("<", "&lt;").replace(">", "&gt;");
		String title = request.getParameter("title").trim().replace("<", "&lt;").replace(">", "&gt;");
		String content = request.getParameter("content").trim().replace("<", "&lt;").replace(">", "&gt;");
		
		//DTO 인스턴스를 생성하여 반환받은 전달값으로 필드값 변경
		GuestDTO guest = new GuestDTO();
		guest.setName(name);
		guest.setTitle(title);
		guest.setContent(content);
		
		//GUEST 테이블에 방명록 게시글을 저장하는 DAO 클래스의 메소드 호출
		GuestDAO.getDAO().insertGuest(guest);
		
		//클라이언트에 출력페이지(select.itwill)에 대한 요청 URL 주소 전달 - 리다이렉트 이동
		response.sendRedirect("select.itwill");
	}

}
