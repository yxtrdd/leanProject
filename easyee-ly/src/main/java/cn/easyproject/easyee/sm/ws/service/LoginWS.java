package cn.easyproject.easyee.sm.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(name = "LoginWS")
public interface LoginWS {
    /**
     * 登录
     * @param params 用户名密码加密传输
     * @return 验证成功返回true，验证失败返回false
     */
    @WebMethod
    String login(@WebParam(name = "params") String params);

}