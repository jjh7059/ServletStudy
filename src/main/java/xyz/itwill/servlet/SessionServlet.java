package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//세션을 바인딩하여 클라이언트에게 세션정보를 전달하는 서블릿
//세션(Session) : 서버(웹프로그램)와 클라이언트와의 연결 지속성을 제공하기 위해 클라이언트에
//저장하는 값 - 세션 아이디로 클라이언트를 구분하여 세션값(인스턴스) 저장
//세션 바인딩(Session Binding) : 세션을 웹프로그램에서 사용할 수 있도록 연결하는 작업 - WAS 자동 처리
//클라이언트에서 JSESSIONID 이름의 쿠키를 제공하지 않은 경우 세션을 생성하여 바인딩 처리
// => 생성된 세션의 아이디(식별자)를 JSESSIONID 이름의 쿠키값으로 저장하여 클라이언트에게 전달
//클라이언트에서 JSESSIONID 이름의 쿠키를 제공한 경우 세션 트래킹 후 바인딩 처리
// => 세션 트래킹 후 검색된 세션이 없는 경우 세션을 생성하여 바인딩 처리
//세션 트래킹(Session Tracking) : 클라이언트에서 제공된 JSESSIONID의 쿠키값을 서버에 존재하는
//모든 세션의 아이디와 비교하여 검색하는 작업
@WebServlet("/session.itwill")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//HttpServletRequest.getSession() : 바인딩된 세션(HttpSession 인스턴스)를 반환하는 메소드
		// => HttpSession 인스턴스를 생성하여 바인딩하거나 기존 HttpSession 인스턴스를 바인딩하여 반환
		//세션이 생성되어 바인딩된 경우 생성된 세션의 식별자(ID)를 JSESSIONID 쿠키로 클라이언트에게 전달
		// => JSESSIONID 쿠키는 기본적으로 유지시간이 -1로 설정되어 브라우저 종료시 소멸
		//HttpSession : 세션을 저장하기 위한 인스턴스 - WAS에 의해 관리
		HttpSession session = request.getSession();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>세션(Session)</h1>");
		out.println("<hr>");
		//HttpSession.isNew() : 세션을 트래킹하여 바인딩한 경우 false 반환, 세션을 생성하여
		//바인딩된 경우 true 반환하는 메소드
		if(session.isNew()) {
			out.println("<p>세션을 생성하여 바인딩 하였습니다.</p>");
		} else {
			out.println("<p>세션을 트래킹하여 바인딩 하였습니다.</p>");
		}
		
		//HttpSession.getId() : 바인딩된 세션의 식별자를 반환하는 메소드
		out.println("<p>세션 아이디 = " + session.getId() + "</p>");
		
		//HttpSession.getCreationTime() : 바인딩된 세션이 생성된 시간(TimeStamp)을 반환하는 메소드
		out.println("<p>세션 생성시간 = " + session.getCreationTime() + "</p>");
		
		//HttpSession.getMaxInactiveInterval() : 바인딘된 세션의 유지시간(초)을 반환하는 메소드
		// => 세션 유지시간을 변경하지 않은 경우 기본값은 1800초(30분)으로 설정
		out.println("<p>세션 유지시간 = " + session.getMaxInactiveInterval() + "</p>");
		
		//HttpSession.setAttribute(String name, Object value) : 바인딩된 세션의 세션값을 저장하는 메소드
		// => 세션에 속성명(AttributeName - String)과 속성값(AttributeValue - Instance)을 하나로 묶어서 저장
		// => 세션을 바인딩한 모든 웹 프로그램에서 속성명을 이용하여 속성값을 반환받아 사용 - 공유
		session.setAttribute("now", new Date());
		
		//HttpSession.getAttribute(String name) : 바인딩된 세션에 저장된 속성값(인스턴스)를
		//속성명으로 반환하는 메소드
		// => 속성값은 Object 타입의 인스턴스로 반환되므로 명시적 형변환 필요
		Date now = (Date)session.getAttribute("now");
		out.println("<p>세션에 저장된 인스턴스 = " + now + "</p>");
		
		//HttpSession.removeAttribute(String name) : 바인딩된 세션에 저장된 속성값(인스턴스)를
		//속성명으로 제거하는 메소드
		session.removeAttribute("now");
		
		//HttpSession.invalidate() : 바인딩된 세션을 언바인딩(Unbinding)하고 제거하는 메소드 - 세션 제거
		//session.invalidate();
		
		out.println("</body>");
		out.println("</html>");
	}

}
