package com.jcdjor.dirive.model;

/**
 * 文件属性
 * @author Administrator
 *
 */
public class FileBean {
	//文件名
	String fileName;
	//修改时间
	String date;
	//路径
	String filePath;
	//类型
	String type;
	//大小
	String size;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
}
