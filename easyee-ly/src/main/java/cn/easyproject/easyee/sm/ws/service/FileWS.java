package cn.easyproject.easyee.sm.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import cn.easyproject.easyee.sm.ws.entity.CxfFileWrapper;


@WebService(name = "FileWS")
public interface FileWS {
    /**
     * 文件上传
     * @param file 文件上传包装类
     * @return 上传成功返回true，上传失败返回false。
     */
    @WebMethod
    boolean upload(@WebParam(name = "file") CxfFileWrapper file,@WebParam(name = "params") String params);

    /**
     * 文件下载
     * @param params 
     * @return 文件
     */
    @WebMethod
    CxfFileWrapper download(@WebParam(name = "params") String params);
}