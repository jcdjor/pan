package com.jcdjor.dirive.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcdjor.dirive.util.HDFSUtil;

/**
 * Servlet implementation class MkDirsServlet
 */
@WebServlet("/MkDirsServlet")
public class MkDirsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MkDirsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����������
		response.setHeader("Access-Control-Allow-Origin", "*");
		//�������
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		// ���ñ���
		response.setContentType("text/html;charset=utf-8");
		//����ʱ�ı���
		request.setCharacterEncoding("utf-8");
		//��ȡǰ̨·��
		String dirPath = request.getParameter("dirPath");
		System.out.println(dirPath);
		
		boolean bool = HDFSUtil.mkdirs(dirPath);
		
		PrintWriter res = response.getWriter() ;
		if (bool) {
			res.write("success");
		} else {
			res.write("error");
		}
		res.close();
		
	}

}
