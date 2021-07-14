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

//WAS(Apache Tomcat)���� �����ϴ� DBCP ���̺귯���� Ŭ������ �̿��Ͽ� ConnectionPool�� �����ϰ�
//Connection �ν��Ͻ��� �����޾� Ŭ���̾�Ʈ���� �����ϴ� ����
@WebServlet("/dbcp.itwill")
public class DbcpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//BasicDataSource �ν��Ͻ� ���� - ConnectionPool ����� �����ϴ� �ν��Ͻ�
		BasicDataSource dataSource = new BasicDataSource();
		
		//Connection �ν��Ͻ��� �����ϱ� ���� BasicDataSource �ν��Ͻ��� �ʵ尪 ����
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("scott");
		dataSource.setPassword("tiger");
		dataSource.setInitialSize(10);//���� �����Ǵ� Connection �ν��Ͻ��� ���� ����
		dataSource.setMaxIdle(10);//�������� Connection �ν��Ͻ��� �ִ� ���� ����
		dataSource.setMaxTotal(15);//�ִ� ���� ������ Connection �ν��Ͻ��� ���� ����
		
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
			out.println("<h3>Connection �ν��Ͻ� ���� ��</h3>");
			out.println("<p>Active Connection Number = " + dataSource.getNumActive() + "</p>");
			out.println("<p>Idle Connection Number = " + dataSource.getNumIdle() + "</p>");
			
			con.close();
			
			out.println("<hr>");
			out.println("<h3>Connection �ν��Ͻ� ���� ��</h3>");
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
