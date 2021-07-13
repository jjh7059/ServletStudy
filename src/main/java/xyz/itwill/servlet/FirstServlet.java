package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet : 서블릿 클래스를 서블릿으로 등록하고 URL 주소를 매핑하기 위한 어노테이션(Annotation)
// => [web.xml] 파일의 servlet 엘리먼트와 servlet-mapping 엘리먼트와 동일한 기능 제공
//어노테이션 속성을 이용하여 서블릿 이름 또는 URL 주소 에 대한 매핑 처리 등을 설정
// => name 속성값으로 서블릿 이름을 설정(선택) : name 속성을 생략하면 클래스 이름을 서블릿 이름으로 자동설정
// => value 속성값으로 URL 주소를 매핑 처리(필수) : 다른 속성이 없는 경우 속성값만 설정 가능
// => 어노테이션 속성은 , 기호로 구분하여 설정
@WebServlet("/first.itwill")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//클라이언트의 모든 요청방식에 대한 요청 처리와 응답을 제공하는 메소드
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답 결과를 생성하지 않을 경우 생략 가능
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//웹 프로그램을 실행 - 요청에 대한 처리 후 클라이언트에게 결과 제공
		// => 클라이언트에게 일관성 있는 정보를 제공하는 동적 페이지를 구현하여 제공
		Date now = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		String displayNow = simpleDateFormat.format(now);
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Servlet</title>");
		out.println("<style type='text/css'>");
		out.println("p { width: 600px; margin: 0 auto; padding: 30px 0; font-size: 2em;"
				+ "font-weight: bold; text-align: center; border: 2px solid black;");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>서블릿 시계</h1>");
		out.println("<hr>");
		out.println("<p>" + displayNow + "</p>");
		out.println("<script type='text/javascript'>");
		out.println("setInterval(function() { location.reload(); }, 1000);");
		out.println("</script>");
		out.println("</body>");
		out.println("</html>");
	}

}
