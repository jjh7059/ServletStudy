package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//서블릿(웹 프로그램)은 클라이언트 요청에 의해 WAS(Web Application Server)에 등록된 서블릿
//클래스를 읽어 메모리에 저장하고 인스턴스를 생성하여 service 메소드를 호출하여 요청에
//대한 처리와 응답 결과 제공
// => 서블릿 클래스의 인스턴스가 이미 존재할 경우 인스턴스를 생성하지 않고 기존 인스턴스로 service() 메소드 호출
// => WAS의 주요 기능 중 하나는 서블릿 관련 인스턴스를 관리하는 컨테이너(Container) 기능
//컨테이너(Container) : 인스턴스의 생명주기(LifeCycle)를 관리(생성,사용,소멸)하는 프로그램
@WebServlet("/life.itwill")
public class LifeCycleServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private String name;
	
	//생성자(Constructor) : 인스턴스를 생성하기 위한 메소드 - 초기화 작업 : 필드 초기화
	// => 클라이언트 요청시 WAS에 의해 서블릿 클래스를 인스턴스로 생성할 때 한번만 호출
	public LifeCycleServlet() {
		System.out.println("### LifeCycleServlet 클래스의 생성자 호출");
		//name = "홍길동";
	}
	
	//init() : WAS가 서블릿 클래스를 인스턴스로 생성하고 가장 먼저 한번만 호출하는 메소드
	// => 생성자 대신 init() 메소드로 필드 초기화 작업
	//생성자 대신 init() 메소드로 초기화 작업을 하는 이유는 ServletConfig 인스턴스를 
	//매개변수로 제공받아 사용하여 필드 초기화 작업이 가능
	//ServletConfig : 컨텍스트 관련 환경정보를 저장하는 인스턴스
	//=> [web.xml] 파일에 설정된 정보를 제공받아 사용 가능
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("### LifeCycleServlet 클래스의 init() 메소드 호출 ###");
		//name = "홍길동";
		
		//ServletConfig.getServletContext() : ServletContext 인스턴스를 반환하는 메소드
		//getServletContext.getInitParameter(String parameterName) : [web.xml] 파일의 context-param
		//엘리먼트로 제공되는 값을 얻어와 반환하는 메소드
		name = config.getServletContext().getInitParameter("name");
	}
	
	//service() : 클라이언트 요청마다 WAS에 의해 반복적으로 호출되는 메소드
	// => 클라이언트 요청에 대한 처리와 실행결과를 생성하여 응답
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("### LifeCycleServlet 클래스의 service() 메소드 호출 ###");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>서블릿의 생명주기</h1>");
		out.println("<hr>");
		out.println("<p>" + name + "님, 안녕하세요.</p>");
		out.println("<img src='image.itwill' width='300'>");
		out.println("</body>");
		out.println("</html>");
	}
	
	//destroy() : 서블릿 인스턴스 소멸 전 한번만 호출되는 메소드 - 마무리 작업
	// => WAS 종료시 WAS에 의해 생성된 인스턴스는 자동 소멸
	@Override
	public void destroy() {
		System.out.println("### LifeCycleServlet 클래스의 destroy() 메소드 호출 ###");
	}

}
