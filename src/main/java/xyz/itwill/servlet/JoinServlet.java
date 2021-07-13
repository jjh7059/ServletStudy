package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//입력 페이지(form.html)에서 전달된 입력값을 반환받아 클라이언트에게 전달하는 서블릿
@WebServlet("/join.itwill")
public class JoinServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//입력 페이지(form.html)의 from 태그를 이용하여 [POST] 방식으로 요청하여 실행되는 서블릿
		// => 서블릿을 [GET]방식으로 요청한 경우 비정상적인 요청
		//웹 프로그램을 비정상적인 방법으로 요청한 경우 에러코드를 전달하거나 에러페이지로 이동 처리
		//HttpServletRequest.getMethod() : 클라이언트의 요청방식을 반환하는 메소드
		if(request.getMethod().equals("GET")) {//클라이언트가 [GET] 방식으로 요청한 경우
			//HttpServletResponse.sendError(int statusCode) : 클라이언트에게 에러코드
			//(StatusCode - 4XX or 5XX)를 전달하는 메소드
			//에러코드는 HttpServletResponse 인터페이스의 상수(Constant Field)로 제공
			//response.sendError(HttpServletResponse.SC_BAD_REQUEST);//400
			//response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405
			//return;//서블릿 종료
			
			//HttpServletResponse.sendRedirect(String url) : 클라이언트에게 301 상태코드와
			//URL 주소를 전달하는 메소드 - 리다이렉트 이동
			// => 301 : 전달받은 URL 주소의 웹 프로그램을 요청하기 위한 상태코드
			// => 클라이언트 브라우저에 URL 주소를 변경하고 요청한 웹 프로그램의 응답결과를 출력 - 페이지 이동
			// => 서블릿을 이용한 페이지 이동 방법 - 리다이렉트 이동
			response.sendRedirect("error.html");
			return;
			
			
		}
		

		//POST 방식으로 웹프로그램을 요청한 경우 리퀘스트 메시지의 BODY에 입력값을 저장하여 전달
		// => 리퀘스트 메시지의 BODY의 기본 캐릭터셋은 ISO-8859-1(서유럽어)로 설정
		//HttpServletRequest.setCharacterEncoding(String encoding) : 리퀘스트 메시지의 BODY로
		//전달되는 값에 대한 캐릭터셋을 변경하는 메소드
		request.setCharacterEncoding("utf-8");
		
		//서블릿 요청에 대한 전달값(사용자 입력값)을 반환받아 저장
		//HttpServletRequest.getParameter(String name) : 웹 프로그램 요청시 전달된 값을 얻어와
		//반환하는 메소드 - 문자열(String) 반환
		// => GET : QueryString으로 전달된 값의 이름(식별자)을 이용하여 전달값 반환
		// => POST : 입력 태그의 name 속성값을 이용하여 전달값 반환
		// => 전달값이 존재하지 않을 경우 null 반환하며 GET 방식에서 전달값이 없는 경우 NullString 반환
		String id = request.getParameter("id");
		
		if(id == null || id.equals("")) {//전달값이 없는 경우 - 비정상적인 요청
			response.sendRedirect("error.html");
			return;
		}
		
		if(!Pattern.matches("^[a-zA-Z]\\w{5,19}$", id)) {//전달값이 형식에 맞지 않을 경우
			response.sendRedirect("error.html");
			return;
		}
		
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		String sex = request.getParameter("sex");
		String job = request.getParameter("job");
		//같은 이름으로 전달된 값이 있는 경우 첫번째 전달값만 반환받아 저장
		//String hobby = request.getParameter("hobby");
		//HttpServletRequest.getParameterValues(String name) : 같은 이름으로 전달되는
		//모든 값들을 배열로 반환하는 반환하는 메소드
		// => 다중 선택 기능의 입력태그로 전달되는 값을 반환받을 때 사용하는 메소드
		String[] hobby = request.getParameterValues("hobby");
		String profile = request.getParameter("profile");
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>회원정보 확인</h1>");
		out.println("<hr>");
		out.println("<p>아이디 = " + id + "</p>");
		out.println("<p>비밀번호 = " + pass + "</p>");
		out.println("<p>이름 = " + name + "</p>");
		out.println("<p>주소 = " + addr + "</p>");
		out.println("<p>성별 = " + sex + "</p>");
		out.println("<p>직업 = " + job + "</p>");
		//out.println("<p>취미 = " + hobby + "</p>");
		if(hobby == null) {
			out.println("<p>취미 = 미선택</p>");
		} else {
			out.println("<p>취미 = ");
			for(int i = 0; i < hobby.length; i++) {
				out.println(hobby[i]);
				if(i < hobby.length - 1) {//배열의 마지막 요소가 아닌 경우
					out.println(",");
				}
			}
			out.println("</p>");
		}
		//textarea 태그로 입력되어 전달된 값은 (Enter)를 <br> 태그로 변환하여 출력
		out.println("<p>자기소개<br>" + profile.replace("\n", "<br>") + "</p>");
		out.println("</body>");
		out.println("</html>");
	}
}
