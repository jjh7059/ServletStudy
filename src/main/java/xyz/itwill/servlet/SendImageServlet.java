package xyz.itwill.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//클라이언트 요청에 대한 이미지 파일로 응답하기 위한 서블릿
@WebServlet("/image.itwill")
public class SendImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트에게 이미지 파일로 응답되도록 설정
		// => 이미지 파일로 응답하므로 캐릭터셋 미설정
		response.setContentType("image/jpeg");
		
		//클라이언트에게 응답될 이미지 파일을 생성하기 위한 출력 스트림을 반환받아 저장
		ServletOutputStream out = response.getOutputStream();
		
		//서버에 저장된 이미지의 시스템 경로를 반환받아 저장
		//HttpServletRequest.getServletContext() :  ServletContext 인스턴스를 반환하는 메소드
		//ServletContext : 클라이언트가 요청한 컨텍스트의 정보를 저장한 인스턴스
		// => 컨텍스트(Context) : 웹 관련 자원이 저장된 그룹 - 이클립스의 프로젝트
		//getServletContext().getRealPath(String contextFilePath) : 컨텍스트에 존재하는 파일의
		//시스템 경로를 반환하는 메소드
		String filePath = request.getServletContext().getRealPath("/WEB-INF/Koala.jpg");
		//System.out.println("filePath = " + filePath);
		
		//서버의 이미지 파일을 읽기 위한 입력 스트림을 생성하여 저장
		FileInputStream in = new FileInputStream(filePath);
		
		//입력 스트림(서버 이미지 파일)에서 원시 데이터를 읽어 출력스트림(클라이언트 
		//응답 파일)으로 전달 - 반복 처리
		while(true) {
			int readByte = in.read();
			if(readByte == -1) break;
			out.write(readByte);
		}
		
		//입력 스트림 제거
		in.close();
	}
}
