package xyz.itwill.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//입력페이지(file_view.html)에서 입력되어 전달된 값과 입력파일을 반환받아 클라이언트에게 전달하는 서블릿
@WebServlet("/view.itwill")
public class FileViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//비정상적인 요청에 대한 처리
		if(request.getMethod().equals("GET")) {
			response.sendRedirect("file_view.html");//입력페이지로 이동
			return;
		}
		
		//입력값에 대한 캐릭터셋 변경
		request.setCharacterEncoding("utf-8");
		
		//전달값을 반환받아 저장
		//멀티파트 폼데이터로 전달된 값은 HttpServletRequest 인스턴스의 getParameter()
		//메소드로 호출하여 반환 불가능 
		//String name = request.getParameter("name");
		//String fileone = request.getParameter("fileone");
		
		//처리 결과 전달
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>입력값과 입력파일</h1>");
		out.println("<hr>");
		/*
		out.println("<p>올린이 = " + name + "</p>");
		out.println("<p>파일명 = " + fileone + "</p>");
		 */
		
		//멀티파트 폼데이터로 전달된 값을 읽기 위한 입력 스트림을 반환받아 저장
		//HttpServletRequest.getInputStream() : 리퀘스트 메시지 BODY에 저장되어 전달된
		//멀티파트 폼데이터를 읽기 위한 입력 스트림(ServletInputStream 인스턴스)을 반환하는 메소드
		//ServletInputStream in = request.getInputStream();
		InputStreamReader in = new InputStreamReader(request.getInputStream(), "utf-8");
		
		//입력 스트림을 이용하여 멀티파트 폼데이터를 얻어와 클라이언트에게 전달
		out.println("<p>[멀티파트 폼데이터 내용]</p>");
		out.println("<pre>");
		while(true) {
			int readByte = in.read();
			if(readByte == -1) break;
			out.write(readByte);
		}
		out.println("</pre>");
		
		out.println("</body>");
		out.println("</html>");
	}
}
