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
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//允许跨域请求
		response.setHeader("Access-Control-Allow-Origin", "*");
		//请求放行
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		// 设置编码
		response.setContentType("text/html;charset=utf-8");
		//请求时的编码
		request.setCharacterEncoding("utf-8");
		//获取前台路径
		String path = request.getParameter("path");
		System.out.println(path);
		
		boolean bool = HDFSUtil.delete(path);
		
		PrintWriter res = response.getWriter() ;
		
		if (bool) {
			res.write("success");
		} else {
			res.write("error");
		}
		res.close();
		
	}

}
