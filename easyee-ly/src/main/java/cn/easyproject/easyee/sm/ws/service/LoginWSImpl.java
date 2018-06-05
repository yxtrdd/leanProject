package cn.easyproject.easyee.sm.ws.service;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.util.Base64Util;
import cn.easyproject.easyee.sm.ws.entity.JsonBo;
import net.sf.json.JSONObject;

@WebService(endpointInterface = "cn.easyproject.easyee.sm.ws.service.LoginWS", portName = "LoginWSPort", serviceName = "LoginWSService")
@Service("loginWS")
public class LoginWSImpl implements LoginWS {
	
	@Override
	public String login(String params) {
		String success = "false";
		String message = "登录失败";
		
		JsonBo json = new JsonBo();
		try {
			byte[] str = Base64Util.decode(params);
			String paramStr = new String(str);
			if(null != paramStr && paramStr.contains("&")){
				String[] strs = paramStr.split("&");
				if(null !=strs && strs.length ==2){
					String lname = (strs[0].split("="))[1];
					String pwd = (strs[1].split("="))[1];
					
					success = "true";
					message = "登录成功";
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		json.setSuccess(success);
		json.setMessage(message);
		
		JSONObject obj = JSONObject.fromObject(json);
		return obj.toString();
	}

}