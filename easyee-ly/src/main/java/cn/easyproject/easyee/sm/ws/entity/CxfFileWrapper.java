package cn.easyproject.easyee.sm.ws.entity;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * CXF上传和下载文件对象包装类 由于CXF的DataHandler无法获取文件名和文件类型，需要在上传和下载时附带文件名
 * 
 * @author accountwcx@qq.com
 */
@XmlType(name = "CxfFileWrapper")
public class CxfFileWrapper {
	// 文件ID
	private String fileId;
	// 文件名
	private String fileName;
	// 文件扩展名
	private String fileExtension;
	// 文件二进制数据
	private DataHandler file;
	// 状态标识 true成功   false失败
	private String success;
	// 状态描述
	private String message;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// 注解该字段为二进制流
	@XmlMimeType("application/octet-stream")
	public DataHandler getFile() {
		return file;
	}

	public void setFile(DataHandler file) {
		this.file = file;
	}

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
	
}