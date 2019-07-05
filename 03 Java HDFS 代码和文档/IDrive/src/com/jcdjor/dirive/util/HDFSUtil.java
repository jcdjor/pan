package com.jcdjor.dirive.util;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import com.jcdjor.dirive.model.FileBean;


public class HDFSUtil {
	private static final String HDFSURL = "hdfs://192.168.66.11:9000";
	
	/**
	 * �ϴ��ļ�
	 * @param hdfsFile	hdfs���ļ�
	 * @param localFile	�����ļ�
	 */
	public static void upload(String localFile, String hdfsFile) {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf, "bigdata");
			fs.copyFromLocalFile(false, false, new Path(localFile), new Path(hdfsFile));
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ�ļ���Ŀ¼
	 * @param path	�鿴��·��
	 */
	public static List<FileBean> list(String path) {
		List<FileBean> fileList = null;
		try {
			// 1.hadoop����
			Configuration conf = new Configuration();
			// 2.get�ͻ���
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);	
			// 3.��ȡ�б�
			FileStatus[] files = fs.listStatus(new Path(path));
			// ʵ���������б�
			fileList = new ArrayList<FileBean>();
			//ѭ����ȡ�ļ��б�����
			for(FileStatus file : files) {
				//�ļ���
				String fileName = file.getPath().getName();
				//·��
				String filePath = file.getPath().getParent().toString();
				filePath = filePath.replace(HDFSURL, "");
				//�޸�ʱ��
				long dateTmp = file.getModificationTime();
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTmp);
				//����
				String type = "fa-file-text-o";
				if(file.isDirectory()) {	//�ļ���
					type = "fa-folder-o";
				}else {
					if(fileName.endsWith("txt")) {
						type = "fa-file-text-o";
					}else if(fileName.endsWith("doc") || fileName.endsWith("docx")) {
						type = "fa-file-word-o";
					}else if(fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
						type = "fa-file-excel-o";
					}else if(fileName.endsWith("ppt") || fileName.endsWith("pptx")) {
						type = "fa-file-powerpoint-o";
					}else if(fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("jpeg")) {
						type = "fa-file-image-o";
					}else if(fileName.endsWith("mp3") || fileName.endsWith("amr")) {
						type = "fa-file-audio-o";
					}else if(fileName.endsWith("mp4") || fileName.endsWith("avi") || fileName.endsWith("wmv")) {
						type = "fa-file-video-o";
					}if(fileName.endsWith("pdf")) {
						type = "fa-file-pdf-o";
					}if(fileName.endsWith("zip") || fileName.endsWith("rar") || fileName.endsWith(".7z")) {
						type = "fa-file-archive-o";
					}else {
						type = "fa-file-text-o";
					}
				}
				//��С
				String size = getSize(file.getLen());
				
				//��װ�ļ�����
				FileBean fd = new FileBean();
				fd.setFileName(fileName);
				fd.setDate(date);
				fd.setFilePath(filePath);
				fd.setType(type);
				fd.setSize(size);
				//��ӵ������б�
				fileList.add(fd);
				
			}
			fs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}
	
	
	/**
	 * ��ʾ�ļ��б�ֻ��ʾ�ļ���
	 * @param path	
	 */
	public static List<FileBean> listFiles(String path) {
		List<FileBean> fileList = null;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);
			//��ȡ�ļ�Ŀ¼�µ������ļ�������Ŀ¼
			 RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path(path), true);
			//ʵ���������б�
			fileList = new ArrayList<FileBean>();
			 //����ʱ���ʽ
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 //ѭ��Ŀ¼�µ������ļ�
			 while(files.hasNext()) {
				LocatedFileStatus fileStatus = files.next();
				//�ļ���
				String fileName = fileStatus.getPath().getName();
				//·��
				String filePath = fileStatus.getPath().getParent().toString();
				filePath = filePath.replace(HDFSURL, "");
				//�޸�ʱ��
				long dateTmp = fileStatus.getModificationTime();
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTmp);
				//����
				String type = "fa-file-image-o";
				
				if(fileName.endsWith(".txt")) {
					type = "fa-file-text-o";
				}else if(fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
					type = "fa-file-word-o";
				}else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
					type = "fa-file-excel-o";
				}else if(fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
					type = "fa-file-powerpoint-o";
				}else if(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
					type = "fa-file-image-o";
				}else if(fileName.endsWith(".mp3") || fileName.endsWith(".amr")) {
					type = "fa-file-audio-o";
				}else if(fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".wmv")) {
					type = "fa-file-video-o";
				}if(fileName.endsWith(".pdf")) {
					type = "fa-file-pdf-o";
				}if(fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".7z")) {
					type = "fa-file-archive-o";
				}else {
					type = "fa-file-text-o";
				}
				 
				//��С
				String size = getSize(fileStatus.getLen());
				
				//��װ�ļ�����
				FileBean fd = new FileBean();
				fd.setFileName(fileName);
				fd.setDate(date);
				fd.setFilePath(filePath);
				fd.setType(type);
				fd.setSize(size);
				//��ӵ������б�
				fileList.add(fd);
					 
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return fileList;
	}
	
	/**
	 * ɾ���ļ����ļ���
	 * @param file
	 */
	public static boolean delete(String file) {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf,"bigdata");
			//ɾ���ļ���true����ݹ�ɾ��
			fs.delete(new Path(file), true);
			//�Ͽ�����
			fs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	/**
	 * 
	 * @param dirPath	������Ŀ¼��·��
	 * @return
	 */
	public static boolean mkdirs(String dirPath) {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf,"bigdata");
			//�����ļ���
			fs.mkdirs(new Path(dirPath));
			fs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * �ֽڴ�Сת��
	 * @param sizeTmp
	 * @return
	 */
	public static String getSize(long sizeTmp) {
        //������㳣��
		long GB = 1024 * 1024 * 1024;
		long MB = 1024 * 1024;
		long KB = 1024;
        
        DecimalFormat df = new DecimalFormat("#.000");	//��ʽ��С��
        String size = "";
        if (sizeTmp / GB >= 1) {
            //�����ǰByte��ֵ���ڵ���1GB
        	size = df.format(sizeTmp / (float) GB) + "GB   ";
        } else if (sizeTmp / MB >= 1) {
            //�����ǰByte��ֵ���ڵ���1MB
        	size = df.format(sizeTmp / (float) MB) + "MB   ";
        } else if (sizeTmp / KB >= 1) {
            //�����ǰByte��ֵ���ڵ���1KB
        	size = df.format(sizeTmp / (float) KB) + "KB   ";
        } else {
        	size = sizeTmp + "B   ";
        }
        return size;  
    }
	
}
