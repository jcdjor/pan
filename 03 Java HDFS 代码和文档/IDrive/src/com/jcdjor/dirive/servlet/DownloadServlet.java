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
	//hadoop���ӵ�ַ
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
		//�����������
		response.setHeader("Access-Control-Allow-Origin", "*");
		//�������
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		//��ȡǰ̨����,�ļ������ļ�·��
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		//�ļ�·��
		String path = filePath+'/'+fileName;
		System.out.println(path);
		       
		// ���ñ���
		response.setContentType("text/html;charset=utf-8");
		//����ʱ�ı���
		request.setCharacterEncoding("utf-8");
		//�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ����� 
        response.setContentType("application/multipart/form-data");
        
        //�����ļ�ͷ�����һ�����������������ļ���(�������ǽ�a.ini)
        response.setHeader ("Content-Disposition", "attachment;filename=" + new String (fileName.getBytes ("utf-8"), "ISO8859-1"));
        try {
	        //����hadoop
			Configuration conf = new Configuration();
			//��ȡ������
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);
			//��ȡ�ļ������
			FSDataInputStream in = fs.open(new Path(path));
			//��ȡ�ļ�������
			ServletOutputStream out = response.getOutputStream();
			//��ͨ��
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
