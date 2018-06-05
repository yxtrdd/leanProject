package cn.easyproject.easyee.sm.ws.service;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.ws.entity.CxfFileWrapper;

@WebService(endpointInterface = "cn.easyproject.easyee.sm.ws.service.FileWS", portName = "FileWSPort", serviceName = "FileWSService")
@Service("fileWS")
public class FileWSImpl implements FileWS {
	
	@Override
	public boolean upload(CxfFileWrapper file,String params) {
		boolean result = false;

		return result;
	}

	@Override
	public CxfFileWrapper download(String params) {
		CxfFileWrapper fileWrapper = new CxfFileWrapper();
		String success = "false";
		String message = "下载文件失败";
		
		fileWrapper.setSuccess(success);
		fileWrapper.setMessage(message);
		return fileWrapper;
	}

}