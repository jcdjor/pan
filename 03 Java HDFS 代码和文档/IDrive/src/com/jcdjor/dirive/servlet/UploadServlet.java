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
 * ���ڽ����ļ��ϴ���Ŀǰ�ǵ��ļ��ϴ���
 * @MultipartConfig ��ע��Ҫ��Ϊ�˸���Servlet3.0��HttpServletRequest�ṩ�Ķ��ϴ��ļ���֧��
 */
@MultipartConfig
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//hadoop���ӵ�ַ
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
		//�����������
		response.setHeader("Access-Control-Allow-Origin", "*");
		//�������
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		// ���ñ���
		response.setContentType("text/html;charset=utf-8");
		//����ʱ�ı���
		request.setCharacterEncoding("utf-8");
		try {
			//�õ��ϴ��ļ���Ŀ¼
			String path = request.getParameter("path");
			//�õ��ϴ����ļ�
			Part part = request.getPart("myfile");
			//��ȡ�ϴ��ļ���
			String submitName = part.getSubmittedFileName();
			//�ļ��ϴ�·��
			String filePath = path+'/'+submitName;
            
            
			if(!(submitName == null || submitName.isEmpty())){
				//����ϴ��ļ���ͷ��Ϣ
	            String contentDesc = part.getHeader("content-disposition");
	            System.out.println("content-disposition"+ contentDesc);
	            
	            //hadoop
	            Configuration conf = new Configuration();
				FileSystem fs = FileSystem.get(new URI(HDFSURL), conf, "bigdata");
				//���ļ�д�����·����
	            InputStream in = part.getInputStream();
	            //��Hdfs�ϴ���һ���ļ������������
	            FSDataOutputStream out = fs.create(new Path(filePath));
	            
	            System.out.println(filePath);
	            
	            //���� ===>  ���
	    		IOUtils.copyBytes(in, out, 4096, true);
	    		PrintWriter res = response.getWriter() ;
				res.write("�ܺã��ļ��ϴ��ɹ���");
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
