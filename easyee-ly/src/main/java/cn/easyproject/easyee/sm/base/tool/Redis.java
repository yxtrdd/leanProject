package cn.easyproject.easyee.sm.base.tool;

import cn.easyproject.easyee.sm.base.util.RedisUtil;
import cn.easyproject.easyee.sm.base.util.SpringContextUtil;
import cn.easyproject.easyee.sm.hr.entity.Dept;
import cn.easyproject.easyee.sm.hr.service.DeptService;

/**  
 * description: Redis缓存 <br/>  
 * date: 2017年8月16日 下午7:25:33 <br/>  
 * author: gaojx  <br/> 
 * copyright: 北京志诚泰和信息技术有限公司
 */

public class Redis {
   
    public static RedisUtil redis;
    public static Long expireTime = new Long(8 * 3600);
    public static int excptionNum = 10;
    
    public static String KEY_MODEL = "MODEL";
    public static String KEY_METHOD = "METHOD";
    public static String KEY_IF = "IF";
    public static String KEY_USER = "USER";
    
    static {
       redis= (RedisUtil)SpringContextUtil.getBeanByClass(RedisUtil.class);
    }
    
    /**
     * 通过主键获取值
     * @author gaojx  
     * @param object 实体
     * @param code  业务主键
     * @return
     */
    public static String getNameByCode(Class<?> clazz, String code) {
        if(null == code || "".equals(code)){
            return "";
        }
        String key = getRedisKey(clazz.getSimpleName(), "getNameByCode", code);
        if (redis.exists(key)) {
            return redis.getString(key);
        } else {
            String name = null;
            if (clazz == Dept.class) { // 企业名称
                DeptService deptService = (DeptService) getService(DeptService.class);
                Dept dept = deptService.get(code);
                name = (dept == null ? "" : dept.getDname());
            }
            redis.setString(key, name, new Long(28800));
            return name;
        }
    }
    
    public static Object getService(Class<?> c){
       return SpringContextUtil.getBeanByClass(c);
    }
    
    /**
     * getUserRedisKey:获取用户级的redis缓存key <br/>  
     * @author gaojx  
     * @param model 模块名
     * @param method 方法名
     * @param condition 条件
     * @param user 用户
     * @return
     */
    public static String getUserRedisKey(String model, String method, String condition, String user) {
        StringBuilder key = new StringBuilder();
        if (null != model) {
            key.append(KEY_MODEL + "(" + model + ")");
        }
        if (null != method) {
            key.append(KEY_METHOD + "(" + method + ")");
        }
        if (null != condition) {
            key.append(KEY_IF + "(" + condition + ")");
        }
        if (null != user) {
            key.append(KEY_USER + "(" + user + ")");
        }
        return key.toString();
    }
    
    /**
     * getRedisKey:获取redis缓存key <br/>  
     * @author gaojx  
     * @param model 模块名
     * @param method 方法名
     * @param condition 条件
     * @return
     */
    public static String getRedisKey(String model, String method, String condition) {
        return getUserRedisKey(model, method, condition, "*");
    }
    
}
  
