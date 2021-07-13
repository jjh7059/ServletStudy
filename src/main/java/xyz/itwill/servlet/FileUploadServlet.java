package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

//입력페이지에서 전달된 멀티파트 폼데이터를 처리하기 위한 클래스 필요
// => Apache 그룹에서 배포한 commons-fileupload 라이브러리의 클래스를 사용 - 선택적 파일 업로드
// => Oreilly 그룹에서 배포한 cos 라이브러리의 클래스 사용 - 무조건 파일 업로드

//Oreilly 그룹에서 배포한 cos 라이브러리 파일을 다운로드 받아 프로젝트에 빌드(Build) 처리
//1. http://www.servlets.com >> COS File Upload Library 메뉴 클릭 >> cos-20.08.zip
//2. cos-20.08.zip 압축 해제 >> lib >> cos.jar
//3. 프로젝트 >> src/main/webapp >> WEB-INF >> lib 폴더에 붙여넣기
// => 라이브러리가 자동으로 프로젝트에 빌드 처리 - WEB App Libraries 확인

//입력페이지(file_upload.html)에서 전달된 입력값과 입력 파일명을 반환받아 클라이언트에게 전달하는 서블릿
// => 입력 파일을 전달받아 서버 디렉토리에 저장 - 업로드(Upload)
@WebServlet("/upload.itwill")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 비정상적인 요청에 대한 처리
		if (request.getMethod().equals("GET")) {
			response.sendRedirect("file_upload.html");// 입력페이지로 이동
			return;
		}
		
		//전달된 파일을 저장하기 위한 서버 디렉토리의 시스템 경로를 반환받아 저장
		// => 작업 디렉토리(WorkSpace)가 아닌 웹 디렉토리(WebApps)의 시스템 경로 반환
		String saveDirectory = request.getServletContext().getRealPath("/upload");
		//System.out.println("saveDirectory = " + saveDirectory);
		
		//MultipartRequest 클래스로 인스턴스 생성
		// => MultipartRequest : 멀티파트 폼데이터를 처리하기 위한 기능을 제공하는 인스턴스
		//MultipartRequest(HttpServletRequest request, String saveDirectory
		//[, int maxPostSize][, String encoding][, FileRenamePolicy]) 생성자를 사용하여 인스턴스 생성
		// => request : 요청정보를 저장한 HttpServletRequest 인스턴스
		// => saveDirectory : 전달 파일을 저장하기 위한 서버 디렉토리의 시스템 경로
		// => maxPostSize : 처리 가능한 머리파트 폼데이터의 최대 크기 전달 - 단위 : Byte
		// => encoding : 멀티파트 폼데이터로 전달된 입력값의 인코딩 캐릭터셋을 전달
		MultipartRequest mr = new MultipartRequest(request, saveDirectory, 30*1024*1024, "utf-8");
	}
}
