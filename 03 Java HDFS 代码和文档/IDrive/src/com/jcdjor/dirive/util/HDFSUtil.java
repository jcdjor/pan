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
	 * 上传文件
	 * @param hdfsFile	hdfs的文件
	 * @param localFile	本地文件
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
	 * 显示文件和目录
	 * @param path	查看的路径
	 */
	public static List<FileBean> list(String path) {
		List<FileBean> fileList = null;
		try {
			// 1.hadoop配置
			Configuration conf = new Configuration();
			// 2.get客户端
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);	
			// 3.获取列表
			FileStatus[] files = fs.listStatus(new Path(path));
			// 实例化返回列表
			fileList = new ArrayList<FileBean>();
			//循环获取文件列表属性
			for(FileStatus file : files) {
				//文件名
				String fileName = file.getPath().getName();
				//路径
				String filePath = file.getPath().getParent().toString();
				filePath = filePath.replace(HDFSURL, "");
				//修改时间
				long dateTmp = file.getModificationTime();
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTmp);
				//类型
				String type = "fa-file-text-o";
				if(file.isDirectory()) {	//文件夹
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
				//大小
				String size = getSize(file.getLen());
				
				//封装文件属性
				FileBean fd = new FileBean();
				fd.setFileName(fileName);
				fd.setDate(date);
				fd.setFilePath(filePath);
				fd.setType(type);
				fd.setSize(size);
				//添加到返回列表
				fileList.add(fd);
				
			}
			fs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}
	
	
	/**
	 * 显示文件列表（只显示文件）
	 * @param path	
	 */
	public static List<FileBean> listFiles(String path) {
		List<FileBean> fileList = null;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf);
			//获取文件目录下的所有文件，包括目录
			 RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path(path), true);
			//实例化返回列表
			fileList = new ArrayList<FileBean>();
			 //设置时间格式
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 //循环目录下的所有文件
			 while(files.hasNext()) {
				LocatedFileStatus fileStatus = files.next();
				//文件名
				String fileName = fileStatus.getPath().getName();
				//路径
				String filePath = fileStatus.getPath().getParent().toString();
				filePath = filePath.replace(HDFSURL, "");
				//修改时间
				long dateTmp = fileStatus.getModificationTime();
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTmp);
				//类型
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
				 
				//大小
				String size = getSize(fileStatus.getLen());
				
				//封装文件属性
				FileBean fd = new FileBean();
				fd.setFileName(fileName);
				fd.setDate(date);
				fd.setFilePath(filePath);
				fd.setType(type);
				fd.setSize(size);
				//添加到返回列表
				fileList.add(fd);
					 
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return fileList;
	}
	
	/**
	 * 删除文件或文件夹
	 * @param file
	 */
	public static boolean delete(String file) {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf,"bigdata");
			//删除文件，true代表递归删除
			fs.delete(new Path(file), true);
			//断开连接
			fs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	/**
	 * 
	 * @param dirPath	创建的目录的路径
	 * @return
	 */
	public static boolean mkdirs(String dirPath) {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(new URI(HDFSURL), conf,"bigdata");
			//创建文件夹
			fs.mkdirs(new Path(dirPath));
			fs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 字节大小转换
	 * @param sizeTmp
	 * @return
	 */
	public static String getSize(long sizeTmp) {
        //定义计算常量
		long GB = 1024 * 1024 * 1024;
		long MB = 1024 * 1024;
		long KB = 1024;
        
        DecimalFormat df = new DecimalFormat("#.000");	//格式化小数
        String size = "";
        if (sizeTmp / GB >= 1) {
            //如果当前Byte的值大于等于1GB
        	size = df.format(sizeTmp / (float) GB) + "GB   ";
        } else if (sizeTmp / MB >= 1) {
            //如果当前Byte的值大于等于1MB
        	size = df.format(sizeTmp / (float) MB) + "MB   ";
        } else if (sizeTmp / KB >= 1) {
            //如果当前Byte的值大于等于1KB
        	size = df.format(sizeTmp / (float) KB) + "KB   ";
        } else {
        	size = sizeTmp + "B   ";
        }
        return size;  
    }
	
}
