package xyz.itwill.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//오늘 날짜의 공지사항 파일을 읽어 내용을 클라이언트에게 전달하는 서블릿
// => 오늘 날짜의 공지사항 파일이 없는 경우 공지사항이 없음을 알리는 메시지 전달
// => 공지사항 파일은 /WEB-INF/notice 폴더에 년월일(yyyyMMdd)을 이용하여 작성
@WebServlet("/notice.itwill")
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//현재 날짜를 이용하여 공지사항 파일명(yyyyMMdd.txt)을 생성하여 저장 
		String noticeFileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt";
		//System.out.println("noticeFileName = " + noticeFileName);
		
		//공지사항 파일의 시스템 경로를 반환받아 저장
		String noticeFilePath = request.getServletContext().getRealPath("/WEB-INF/notice/" + noticeFileName);
		//System.out.println("noticeFilePath = " + noticeFilePath);
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1 style='text-align: center;'>공지사항</h1>");
		out.println("<hr>");
		String displayNow = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
		out.println("<p style='text-align: center;'>[" + displayNow + "]의 공지사항</p>");
		try {
			//오늘 날짜의 공지사항 파일을 읽기 위한 입력 스트림을 생성하여 저장
			// => 오늘 날짜의 공지사항 파일이 없는 경우 FileNotFoundException 발생
			BufferedReader in = new BufferedReader(new FileReader(noticeFilePath));
			
			//오늘 날짜의 공지사항 파일의 내용을 읽어 클라이언트에게 전달
			while(true) {
				String text = in.readLine();
				if(text==null) break;
				if(text.equals("")) text = "&nbsp;";
				out.println("<div>" + text + "</div>");
			}
			
			//파일 입력 스트림 제거
			in.close();
		} catch (FileNotFoundException e) {
			out.println("<p>오늘은 공지사항이 없습니다.</p>");
		}
		
		out.println("<br>");
		out.println("<div style='text-align: center;'>");
		out.println("<button type='button' onclick='window.close();'>닫기</button>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
