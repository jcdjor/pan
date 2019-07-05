package com.jcdjor.dirive.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
public class ResponseData {
	public static class STATUS {
		// 正常操作成功
		public static Integer NORMAL = 200;
		// 逻辑操作失败
		public static Integer FAILED = 400;
	}
	// 状濿
    private Integer status;
    // 消息
    private String msg;
    // 数据
    private Object data;

    public ResponseData(Integer status) {
        this.status = status;
    }
    
    /**
     * 操作成功并返回数�?
     * @param data
     * @return
     */
    public static ResponseData success(Object data) {
        ResponseData jsonData = new ResponseData(STATUS.NORMAL);
        jsonData.data = data;
        return jsonData;
    }

    /**
     * 操作成功并返回数�?
     * @param key 数据�?
     * @param val 数据�?
     * @return
     */
    public static ResponseData success(String key, Object value) {
        ResponseData jsonData = new ResponseData(STATUS.NORMAL);
        Map<String, Object> data = new HashMap<>();
        data.put(key, value);
        jsonData.data = data;
        return jsonData;
    }

    /**
     * 操作成功
     * @return
     */
    public static ResponseData success() {
        return new ResponseData(STATUS.NORMAL);
    }

    /**
     * 操作失败并返回消�?
     * @param msg
     * @return
     */
    public static ResponseData fail(String msg) {
        ResponseData jsonData = new ResponseData(STATUS.FAILED);
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * 发鿁消恿
     * @param status 
     * @param msg
     * @return
     */
    public static ResponseData message(Integer status, String msg) {
        ResponseData jsonData = new ResponseData(status);
        jsonData.msg = msg;
        return jsonData;
    }
    
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
