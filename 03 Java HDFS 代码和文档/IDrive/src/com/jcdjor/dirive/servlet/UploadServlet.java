package com.jcdjor.dirive.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Servlet implementation class UploadServlet
 * 用于进行文件上传（目前是单文件上传）
 * @MultipartConfig 标注主要是为了辅助Servlet3.0中HttpServletRequest提供的对上传文件的支持
 */
@MultipartConfig
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//hadoop链接地址
	private static final String HDFSURL = "hdfs://192.168.66.11:9000";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//允许跨域请求
		response.setHeader("Access-Control-Allow-Origin", "*");
		//请求放行
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		// 设置编码
		response.setContentType("text/html;charset=utf-8");
		//请求时的编码
		request.setCharacterEncoding("utf-8");
		try {
			//得到上创文件的目录
			String path = request.getParameter("path");
			//得到上传的文件
			Part part = request.getPart("myfile");
			//获取上传文件名
			String submitName = part.getSubmittedFileName();
			//文件上传路径
			String filePath = path+'/'+submitName;
            
            
			if(!(submitName == null || submitName.isEmpty())){
				//获得上传文件的头信息
	            String contentDesc = part.getHeader("content-disposition");
	            System.out.println("content-disposition"+ contentDesc);
	            
	            //hadoop
	            Configuration conf = new Configuration();
				FileSystem fs = FileSystem.get(new URI(HDFSURL), conf, "bigdata");
				//把文件写到这个路径下
	            InputStream in = part.getInputStream();
	            //在Hdfs上创建一个文件，返回输出流
	            FSDataOutputStream out = fs.create(new Path(filePath));
	            
	            System.out.println(filePath);
	            
	            //输入 ===>  输出
	    		IOUtils.copyBytes(in, out, 4096, true);
	    		PrintWriter res = response.getWriter() ;
				res.write("很好！文件上传成功。");
				res.close();
				out.close();
	    		in.close();
	    		fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
