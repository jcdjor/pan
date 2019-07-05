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
		//�����������
		response.setHeader("Access-Control-Allow-Origin", "*");
		//�������
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		// ���ñ���
		response.setContentType("text/html;charset=utf-8");
		//����ʱ�ı���
		request.setCharacterEncoding("utf-8");
		//��ȡǰ̨·��
		String path = request.getParameter("path");
		System.out.println(path);
		// ��ȡ�ļ��б�
		List<FileBean> list = HDFSUtil.list(path);
		// ��װ��������
		ResponseData respData = ResponseData.success("list", list);
		
		//ת���ַ���
		JSONObject respJson = new JSONObject(respData);
		String respStr = respJson.toString();
		 
		//System.out.println(respStr);
		 
		//���ظ������
		Writer writer = response.getWriter();
		writer.write(respStr);
	}

}
