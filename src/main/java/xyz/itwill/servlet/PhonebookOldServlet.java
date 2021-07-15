package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//PHONEBOOK 테이블에 저장된 모든 행을 검색하여 클라이언트에게 전달하는 서블릿
@WebServlet("/old.itwill")
public class PhonebookOldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//1.OracleDriver 클래스를 읽어 메모리에 저장 - 클래스 로딩
			// => OracleDriver 인스턴스를 생성하여 DriverManager 클래스의 JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2.DriverManager 클래스에 등록된 JDBC Driver를 이용하여 Oracle DBMS Server에
			//접속하여 Connection 인스턴스를 반환받아 저장
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			con = DriverManager.getConnection(url, user, password);
			
			//3.SQL 명령을 저장한 PreparedStatement 인스턴스를 반환받아 저장
			String sql = "select * from phonebook order by phone";
			pstmt = con.prepareStatement(sql);
			
			//4.PreparedStatement 인스턴스에 저장된 SQL 명령을 DBMS 서버에 전달하고 실행결과를 반환
			rs = pstmt.executeQuery();
			
			//5.반환받은 결과를 이용하여 처리 작업 - SQL 실행 결과를 클라이언트에게 HTML 문서로 전달
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'");
			out.println("<title>Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>전화번호부</h1>");
			out.println("<hr>");
			out.println("<table border='1' cellspacing='0'>");
			out.println("<tr>");
			out.println("<th width='200'>전화번호</th>");
			out.println("<th width='200'>이름</th>");
			out.println("<th width='200'>주소</th>");
			out.println("</tr>");
			//ResultSet 커서를 아래로 이동하여 처리행이 있는 경우 반복 처리
			// => ResultSet 커서 위치에 처리행이 없는 경우 반복 종료
			while(rs.next()) {
				out.println("<tr align='center'>");
				//ResultSet 커서 위치의 처리행에 컬럼값을 반환받아 클라이언트에게 전달
				out.println("<td>" + rs.getString("phone") + "</td>");
				out.println("<td>" + rs.getString("name") + "</td>");
				out.println("<td>" + rs.getString("address") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
			
		} catch (ClassNotFoundException e) {
			System.out.println("[에러]OracleDriver 클래스를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("[에러]SQL 오류 = " + e.getMessage());
		} finally {
			//6.JDBC 관련 인스턴스 제거
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			} catch (SQLException e) {}
		}
	}

}
