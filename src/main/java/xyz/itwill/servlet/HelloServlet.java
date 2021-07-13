package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//서블릿(웹에서 실행되는 프로그램) 작성 방법
//1. HttpServlet 클래스를 상속받아 작성 - 서블릿 클래스
// => HttpServlet 클래스를 상속받은 자식 클래스는 객체 직렬화 클래스로 serialVersionUID 필드를 선언하는 것을 권장

public class HelloServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//2. doGet() 또는 doPost() 메소드를 오버라이드 선언
	// => 클라이언트 요청에 대한 처리와 처리결과(HTML)를 생성하기 위한 메소드 - WAS(Web Application Server)에 의해 자동 호출
	// => doGet() : 클라이언트가 GET 방식으로 요청한 경우 호출되는 메소드
	// => doPost() : 클라이언트가 POST 방식으로 요청한 경우 호출되는 메소드
	//doGet() 또는 doPost() 메소드 대신 service() 메소드 오버라이드 선언
	// => service() : 클라이언트의 모든 요청에 의해 호출되는 메소드
	// => doGet() 또는 doPost() 메소드보다 service() 메소드의 호출 순위가 높게 설정
	//WAS에 의해 메소드가 호출될 때 HttpServletRequest 인스턴스와 HttpServletResponse 인스턴스가
	//매개변수에 자동으로 전달되어 저장
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpServletRequest : 리퀘스트 메시지(요청정보)가 저장된 인스턴스
		//HttpServletResponse : 리스폰즈 메시지(응답정보)가 저장된 인스턴스
		
		//3. 클라이언트에게 응답할 문서(파일) 형식(Mime Type) 변경
		// => 문서 형식으로 응답할 경우 문서의 캐릭터셋(CharacterSet : 문자형식 - 인코딩) 설정
		// => 기본 응답 문서 형식 : text/html, 기본 CharacterSet : IOS-8859-1(서유럽어)
		//HttpServletResponse.setContentType(String mimeType[;charset=encoding])
		response.setContentType("text/html;charset=utf-8");
		
		//4. 클라이언트에게 요청에 대한 처리 결과를 전달(응답)하기 위한 출력 스트림을 반환받아 저장
		//형식)HttpServletResponse.getOutputStream() : 원시 데이터(1Byte)를 전달할 수 있는
		//출력 스트림(ServletOutputStream 인스턴스)을 반환하는 메소드
		// => 그림, 음향, 영상 파일 등을 클라이언트에게 전달하기 위해 사용하는 출력스트림
		//형식)HttpServletResponse.getWriter() : 인코딩 처리된 문자 데이터(2Byte)를 전달할 수 있는
		//출력 스트림(PrintWriter 인스턴스)을 반환하는 메소드
		// => PlaneText, HTML, XML 문서 등을 클라이언트에게 전달하기 위해 사용하는 출력스트림
		PrintWriter out = response.getWriter();
		
		//5. 요청 처리 후 처리 결과를 문서(파일)로 생성 - 응답
		// => 출력 스트림을 이용하여 처리 결과를 전달 문서(파일) 생성
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>서블릿(Servlet)</h1>");
		out.println("<hr>");
		out.println("<p>Hello, Servlet!</p>");
		out.println("</body>");
		out.println("</html>");
		
		//6. 서블릿 클래스를 웹 프로그램으로 등록
		// => [web.xml] 파일에서 서블릿 클래스를 서블릿으로 등록하고 URL 주소를 매핑
		// => 매핑된 URL 주소로 클라이언트가 요청하면 WAS는 등록된 서블릿을 실행하여 응답
	}
}
