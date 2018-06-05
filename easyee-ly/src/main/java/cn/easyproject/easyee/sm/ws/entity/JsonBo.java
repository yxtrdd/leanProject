package cn.easyproject.easyee.sm.ws.entity;

import java.io.Serializable;

public class JsonBo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 状态标识 true成功   false失败
	private String success;
	// 状态描述
	private String message;
	// 内容
	private String content;

	public JsonBo() {}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
