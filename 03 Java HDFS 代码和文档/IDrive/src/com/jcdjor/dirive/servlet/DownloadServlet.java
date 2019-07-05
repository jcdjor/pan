package com.jcdjor.dirive.servlet;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//hadoop链接地址
	private static final String HDFSURL = "hdfs://192.168.66.11:9000";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
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
		//获取前台参数,文件名和文件路径
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		//文件路径
		String path = filePath+'/'+fileName;
		System.out.println(path);
		       
		// 设置编码
		response.setContentType("text/html;charset=utf-8");
		//请求时的编码
		request.setCharacterEncoding("utf-8");
		//设置文件ContentType类型，这样设置，会自动判断下载文件类型 
        response.setContentType("application/multipart/form-data");
        
        //设置文件头：最后一个参数是设置下载文件名(假如我们叫a.ini)
        response.setHeader ("Content-Disposition", "attachment;filename=" + new String (fileName.getBytes ("utf-8"), "ISO8859-1"));
        try {
	        //配置hadoop
			Configuration conf = new Configuration();
			//获取服务器
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);
			//获取文件输出流
			FSDataInputStream in = fs.open(new Path(path));
			//获取文件输入流
			ServletOutputStream out = response.getOutputStream();
			//打开通道
			IOUtils.copyBytes(in, out, 4096, true);
			out.close();
			in.close();
			fs.close();
        }catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		
	}

}
