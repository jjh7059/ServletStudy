package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

//WAS(Apache Tomcat)에서 제공하는 DBCP 라이브러리의 클래스를 이용하여 ConnectionPool을 생성하고
//Connection 인스턴스를 제공받아 클라이언트에게 전달하는 서블릿
@WebServlet("/dbcp.itwill")
public class DbcpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//BasicDataSource 인스턴스 생성 - ConnectionPool 기능을 제공하는 인스턴스
		BasicDataSource dataSource = new BasicDataSource();
		
		//Connection 인스턴스를 생성하기 위해 BasicDataSource 인스턴스의 필드값 변경
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("scott");
		dataSource.setPassword("tiger");
		dataSource.setInitialSize(10);//최초 생성되는 Connection 인스턴스의 갯수 변경
		dataSource.setMaxIdle(10);//대기상태의 Connection 인스턴스의 최대 갯수 변경
		dataSource.setMaxTotal(15);//최대 생성 가능한 Connection 인스턴스의 갯수 변경
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Apache DBCP</h1>");
		out.println("<hr>");
		
		try {
			Connection con = dataSource.getConnection();
			out.println("<p>Connection = " + con + "</p>");
			
			out.println("<hr>");
			out.println("<h3>Connection 인스턴스 제공 후</h3>");
			out.println("<p>Active Connection Number = " + dataSource.getNumActive() + "</p>");
			out.println("<p>Idle Connection Number = " + dataSource.getNumIdle() + "</p>");
			
			con.close();
			
			out.println("<hr>");
			out.println("<h3>Connection 인스턴스 제거 후</h3>");
			out.println("<p>Active Connection Number = " + dataSource.getNumActive() + "</p>");
			out.println("<p>Idle Connection Number = " + dataSource.getNumIdle() + "</p>");
			
			dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</body>");
		out.println("</html>");
	}

}
