package com.jcdjor.dirive.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.jcdjor.dirive.model.FileBean;
import com.jcdjor.dirive.util.HDFSUtil;
import com.jcdjor.dirive.util.ResponseData;

/**
 * Servlet implementation class FilesServlet
 */
@WebServlet("/FilesServlet")
public class FilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilesServlet() {
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
		// 获取文件列表
		List<FileBean> list = HDFSUtil.list(path);
		// 封装返回数据
		ResponseData respData = ResponseData.success("list", list);
		
		//转换字符串
		JSONObject respJson = new JSONObject(respData);
		String respStr = respJson.toString();
		 
		//System.out.println(respStr);
		 
		//返回给浏览器
		Writer writer = response.getWriter();
		writer.write(respStr);
	}

}
