package cn.easyproject.easyee.sm.base.controller;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.base.tool.StatusCode;
import cn.easyproject.easyee.sm.base.util.ExcelUtil;
import cn.easyproject.easyee.sm.base.util.GenericsUtil;
import cn.easyproject.easyee.sm.base.util.JSONUtil;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.entity.SysUser;
import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * description: 全局控制器基类 <br/>
 * date: 2017年9月26日 下午6:08:38 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 */
public abstract class BaseController<T, C extends EasyCriteria> {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Web Object
     */
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    protected ServletContext application;
    @Resource
    private MessageSource messageSource;
    @Resource
    protected HttpSession session;
    
    /**
     * Ajax 请求响应信息 <br/>
     * { statusCode:响应状态码; msg: 响应消息; callback: 执行回调函数; locationUrl: 跳转页面 }
     * <br/>
     * 
     * Ajax请求返回的状态码，非Http状态吗 <br/>
     * 200操作正常，300操作失败，301用户超时，404资源没有找到，500服务器遇到了错误，401权限不足 <br/>
     * easyssh.main.js 全局Ajax处理
     */
    private ThreadLocal<Integer> statusCode = new ThreadLocal<Integer>();
    /**
     * Ajax请求返回的提示消息
     */
    private ThreadLocal<String> msg = new ThreadLocal<String>();
    /**
     * 返回信息后的回调函数名
     */
    private ThreadLocal<String> callback = new ThreadLocal<String>();
    /**
     * 返回信息后的回调函数名
     */
    private ThreadLocal<String> locationUrl = new ThreadLocal<String>();

    /**
     * 获取业务实现类
     */
    public abstract BaseService<T> getService();

    /**
     * 获取业务实体类
     */
    public Class<?> getEntityClass() {
        return GenericsUtil.getSuperClassGenricType(getClass());
    }

    /**
     * 在查询前后操作
     */
    public void beforeList(PageBean<T> pb, C c) {
    }
    public void afterList(PageBean<T> pb, Map<Object, Object> m) {
    }

    /* CRUD */

    /**
     * Save
     */
    @RequestMapping(value = "/save", produces = "text/html;charset=UTF-8")
    public String save(T o) {
        // 保存
        try {
            getService().save(o);
            // 处理成功 消息
            setMsgKey("msg.saveSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            setMsgKey("msg.saveFail");
            setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return setJsonMsgStr("rowData", o);
    }

    /**
     * Delete
     */
    @RequestMapping("delete")
    public Map<Object, Object> delete(T o) {
        try {
            getService().delete(o);
        } catch (Exception e) {
            e.printStackTrace();
            setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return setJsonMsgMap();
    }

    /**
     * Delete Batch
     */
    @RequestMapping("deleteBatch")
    public Map<Object, Object> deleteBatch(String[] id) {
        try {
            getService().delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return setJsonMsgMap();
    }

    /**
     * Update
     */
    @RequestMapping(value = "/update", produces = "text/html;charset=UTF-8")
    public String update(T o) {
        try {
            getService().update(o);
            setMsgKey("msg.updateSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            setMsgKey("msg.updateFail");
            setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return setJsonMsgStr();
    }

    /**
     * Get
     */
    @RequestMapping("get")
    public Map<Object, Object> get(Serializable id) {
        T o = null;
        try {
            o = getService().get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setJsonMap("data", o);
    }

    /**
     * List
     */
    @RequestMapping("list")
    public String list(C c) {
        // 分页对象
        PageBean<T> pb = getPageBean();
        
        // 查询前操作
        beforeList(pb, c);
        
        // 分页查询
        getService().findByPage(pb, c);
        
        Map<Object, Object> map = setJsonPaginationMap(pb);
        
        // 查询后操作
        afterList(pb, map);
        
        // 转换为json缓存并返回
        return JSONUtil.beanToJson(map);
    }
    
    /**
     * excel导出
     * @param c
     * @param fileName
     * @param titles
     * @param fields
     */
    @RequestMapping("export")
    public void export(C c, String fileName, String titles, String fields) {
        String[] headers = titles.split(",");   // 表头
        String[] columns = fields.split(",");   // 字段名
        // 获得分页对象
        PageBean<T> pb = getPageBean();
        pb.setPageNo(1);
        pb.setRowsPerPage(ExcelUtil.EXPORT_NUMBER_PER);

        // 查询前操作
        beforeList(pb, c);
        // 分页查询
        getService().findByPage(pb, c);

        int count = pb.getRowsCount();

        if (count > ExcelUtil.EXPORT_NUMBER_PER) {
            // 数据量太大，分多次查询导出
            WritableWorkbook wwb = null;
            OutputStream os = null;
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + new Date().getTime() + ".xls");

                os = response.getOutputStream();
                wwb = Workbook.createWorkbook(os);
                WritableCellFormat wcf = ExcelUtil.getCellStyle();

                // 计算查询次数（即sheet个数）
                int pageTotal = count / ExcelUtil.EXPORT_NUMBER_PER;
                int mod = count % ExcelUtil.EXPORT_NUMBER_PER;
                if (mod > 0) {
                    pageTotal++;
                }
                if (headers != null && headers.length > 0) {
                    for (int i = 1; i <= pageTotal; i++) {
                        if (i > 1) {
                            pb.setPageNo(i);
                            getService().findByPage(pb, c);
                        }
                        // 创建工作表sheet
                        WritableSheet ws = wwb.createSheet("Sheet" + i, i - 1);
                        // 要导出的业务数据
                        List<T> data = pb.getData();
                        // 判断一下表头数组是否有数据
                        boolean bool = ExcelUtil.createSheetHeader(ws, wcf, headers);
                        // 判断是否有数据
                        if (bool && data != null && data.size() > 0) {
                            ExcelUtil.writeDataToSheet(ws, wcf, data, columns);
                        }
                    }
                }
                // 写入Exel工作表
                wwb.write();
                // 关闭Excel工作薄对象
                wwb.close();
                // 清空缓存
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭流
                    if (null != os) {
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 一次性导出
            ExcelUtil.exportExcel(response, fileName, pb.getData(), headers, columns);
        }
    }

    /**
     * 采用jackson将对象转换为字符串
     * @param obj
     * @return
     */
    protected String toJson(Object obj){
        return JSONUtil.beanToJson(obj);
    }
    
    /**
     * JSON数据初始化，包含自定义JSON键值对
     * 
     * @param json数据键值对列表
     *            key,value,key2,value2....
     */
    protected Map<Object, Object> setJsonMap(Object... jsonKeyAndValuePair) {
        Map<Object, Object> jsonMap = new HashMap<Object, Object>();
        if (jsonKeyAndValuePair.length > 0) {
            for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
                jsonMap.put(jsonKeyAndValuePair[2 * i], jsonKeyAndValuePair[2 * i + 1]);
            }
        }
        return jsonMap;
    }

    /**
     * JSON数据初始化，包含自定义JSON键值对，及以下EasySSH Ajax响应信息：<br/>
     * statusCode: 响应状态码; <br/>
     * msg: 响应消息; <br/>
     * callback: 执行回调函数, <br/>
     * locationUrl: 跳转页面 <br/>
     * 
     * @param json数据键值对列表
     *            key,value,key2,value2....
     */
    protected Map<Object, Object> setJsonMsgMap(Object... jsonKeyAndValuePair) {

        Map<Object, Object> jsonMap = new HashMap<Object, Object>();

        if (msg.get() == null) {
            msg.set("");
        }
        if (statusCode.get() == null) {
            statusCode.set(StatusCode.OK);
        }
        if (callback.get() == null) {
            callback.set("");
        }
        if (locationUrl.get() == null) {
            locationUrl.set("");
        }

        jsonMap.put("msg", msg.get());
        jsonMap.put("statusCode", statusCode.get());
        jsonMap.put("callback", callback.get());
        jsonMap.put("locationUrl", locationUrl.get());
        if (jsonKeyAndValuePair.length > 0) {
            for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
                jsonMap.put(jsonKeyAndValuePair[i], jsonKeyAndValuePair[2 * i + 1]);
            }
        }
        clearThreadLocalResponse();
        return jsonMap;
    }

    /**
     * setJsonMsgStr:将map对象转换为JSON字符串.（） easyUI form提交后返回数据使用.
     * 
     * @param jsonKeyAndValuePair
     */
    protected String setJsonMsgStr(Object... jsonKeyAndValuePair) {
        return JSONUtil.beanToJson(this.setJsonMsgMap(jsonKeyAndValuePair));
    }

    /**
     * JSON数据初始化，包含自定义JSON键值对，分页信息，及以下EasySSH Ajax响应信息：<br/>
     * statusCode: 响应状态码; <br/>
     * msg: 响应消息; <br/>
     * callback: 执行回调函数, <br/>
     * locationUrl: 跳转页面 <br/>
     * 
     * @param pb分页对象
     * @param json数据键值对列表
     *            key,value,key2,value2....
     */
    protected Map<Object, Object> setJsonPaginationMap(PageBean<T> pb, Object... jsonKeyAndValuePair) {
        Map<Object, Object> jsonMap = new HashMap<Object, Object>();
        // 分页参数
        jsonMap.put("page", pb.getPageNo());
        jsonMap.put("rows", pb.getData());
        jsonMap.put("total", pb.getRowsCount());

        if (msg.get() == null) {
            msg.set("");
        }
        if (statusCode.get() == null) {
            statusCode.set(StatusCode.OK);
        }
        if (callback.get() == null) {
            callback.set("");
        }
        if (locationUrl.get() == null) {
            locationUrl.set("");
        }

        jsonMap.put("msg", msg.get());
        jsonMap.put("statusCode", statusCode.get());
        jsonMap.put("callback", callback.get());
        jsonMap.put("locationUrl", locationUrl.get());
        if (jsonKeyAndValuePair.length > 0) {
            for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
                jsonMap.put(jsonKeyAndValuePair[i], jsonKeyAndValuePair[2 * i + 1]);
            }
        }
        clearThreadLocalResponse();

        return jsonMap;
    }

    /**
     * 获得分页对象，自动封装客户端提交的分页参数
     */
    public PageBean<T> getPageBean() {
        PageBean<T> pb = new PageBean<>();
        pb.setDialect(dialect);

        /*
         * EasyUI Pagination parameter EasyUI Sort parameter
         */
        int page = ServletRequestUtils.getIntParameter(request, "page", 1);
        int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
        String sort = ServletRequestUtils.getStringParameter(request, "sort", "");
        String order = ServletRequestUtils.getStringParameter(request, "order", "");

        pb.setPageNo(page);
        pb.setRowsPerPage(rows);
        // 分页排序
        // 防止SQL注入过滤
        //sort = StringUtils.filterSQLCondition(sort);
        // 防止SQL注入过滤
        order = StringUtil.filterSQLCondition(order);

        if (StringUtil.isNotNullAndEmpty(sort)) {
            pb.setSort(sort);
        }
        if (StringUtil.isNotNullAndEmpty(order)) {
            pb.setSortOrder(order);
        }
        return pb;
    }

    /**
     * 从Session获得当前登录的用户对象
     */
    public SysUser getLoginUser() {
        HttpSession session = request.getSession();
        SysUser user = new SysUser();
        if (session.getAttribute("USER") instanceof SysUser) {
            user = (SysUser) session.getAttribute("USER");
        } else {
            // 防止devtools 不同类加载器 ClassCastException
            Object o = session.getAttribute("USER");
            Class<?> c = o.getClass();
            try {
                int userId = Integer.valueOf(c.getMethod("getUserId").invoke(o).toString());
                int status = Integer.valueOf(c.getMethod("getStatus").invoke(o).toString());
                String name = c.getMethod("getName").invoke(o).toString();
                String realName = c.getMethod("getRealName").invoke(o).toString();
                String password = c.getMethod("getPassword").invoke(o).toString();

                user.setUserId(userId);
                user.setName(name);
                user.setPassword(password);
                user.setRealName(realName);
                user.setStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * getParameterMap:获取所有请求参数转换为Map <br/>
     * 当传递的参数个数不固定且参数名没有重复时使用 <br/>
     * 注意：参数名称若有重复的只能得到第一个 <br/>
     */
    public Map<String, Object> getParameterMap() {
        Map<String, Object> m = new HashMap<>();
        Enumeration<?> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String paramName = (String) e.nextElement();
            String paramValue = request.getParameter(paramName);
            // 形成键值对应的map
            m.put(paramName, paramValue);
        }
        return m;
    }

    @SuppressWarnings("unchecked")
    public String getParameterStr() {
        Enumeration<String> e = request.getParameterNames();
        List<String> list = Collections.list(e);
        Collections.sort(list);
        StringBuffer buffer = new StringBuffer();
        for (String paramName : list) {
            String paramValue = request.getParameter(paramName);
            // 形成键值对应的map
            buffer.append(paramName + "=" + paramValue + "&");
        }
        buffer.append("CUSER=" + this.getLoginUser().getName()); // 默认条件里添加当前用户
        return buffer.toString();
    }

    /**
     * I18N
     * 
     * @param key
     * @param args
     * @return i18n value
     */
    public String getText(String key, Object[] args) {
        return messageSource.getMessage(key, args, RequestContextUtils.getLocale(request));
    }

    /**
     * I18N
     * 
     * @param key
     * @param args
     * @param defaultMessage
     * @return i18n value
     */
    public String getTextString(String key, Object[] args, String defaultMessage) {
        return messageSource.getMessage(key, args, defaultMessage, RequestContextUtils.getLocale(request));
    }
    /**
     * I18N
     * 
     * @param key
     *            i18n key
     * @return i18n value
     */
    public String getText(String key) {
        return messageSource.getMessage(key, null, RequestContextUtils.getLocale(request));
    }

    /* Ajax 响应信息设置 */

    /**
     * 设置Response 响应状态码
     */
    protected void setStatus(int status) {
        response.setStatus(status);
    }
    /**
     * 获得响应状态码
     */
    public Integer getStatusCode() {
        return statusCode.get();
    }
    /**
     * 设置响应状态码
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode.set(statusCode);
    }
    /**
     * 获得响应消息
     */
    public String getMsg() {
        return msg.get();
    }
    /**
     * 设置响应消息
     */
    public void setMsg(String msg) {
        this.msg.set(msg);
    }
    /**
     * 设置响应消息
     */
    public void setMsgKey(String i18nKey) {
        this.msg.set(getText(i18nKey));
    }
    /**
     * 获得响应回调函数
     */
    public String getCallback() {
        return callback.get();
    }
    /**
     * 设置响应回调函数
     */
    public void setCallback(String callback) {
        this.callback.set(callback);
    }
    /**
     * 获得响应跳转地址
     */
    public String getLocationUrl() {
        return locationUrl.get();
    }
    /**
     * 设置响应跳转地址
     */
    public void setLocationUrl(String locationUrl) {
        this.locationUrl.set(locationUrl);
    }
    /**
     * 内部使用，清除 ThreadLocal 的数据，防止线程池共享导致的其他请求受影响
     */
    private void clearThreadLocalResponse() {
        statusCode.remove();
        msg.remove();
        callback.remove();
        locationUrl.remove();
    }

    /* ### 数据库语言dialect自动获取 ### */

    @Value("${db.url}")
    private String dburl;
    public static int dialect = PageBean.MYSQL_DIALECT; // 数据库类型，EasyMyBatis-Pagination

    @PostConstruct
    public void init() {
        if (dburl == null) {
            throw new RuntimeException("Can not find '${db.url}'!");
        }
        dburl = dburl.toString().replace(".", "").toLowerCase();
        if (dburl.contains("mysql")) {
            dialect = PageBean.MYSQL_DIALECT;
        } else if (dburl.contains("oracle")) {
            dialect = PageBean.ORACLE_DIALECT;
        } else if (dburl.contains("sqlserver")) {
            dialect = PageBean.SQLSERVER_DIALECT;
        } else {
            logger.error("EasyMyBatis-Pagination dbType can not recognition!");
        }
    }

}
