package cn.easyproject.easyee.sm.sys.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.easyproject.easyee.sm.base.tool.Redis;

/**
 * 
 * 公共数据获取控制器，权限要求低
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@RestController
@RequestMapping("common")
public class CommonController {

    /**
     *  年份下拉框数据
     * @author gaojx  
     * @param before 前几年
     * @param after 后几年
     * @return
     */
	@RequestMapping("comboYear")
    public List<Map<String, Object>> comboYear(String before, String after) {
        List<Map<String, Object>> data = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        int currYear = date.get(Calendar.YEAR);
        try {
            // 范围
            int rangeBefore = 0;
            int rangeAfter = 0;
            if(null != before && !"".equals(before)){
                rangeBefore = Integer.valueOf(before);
            }
            if(null != after && !"".equals(after)){
                rangeAfter = Integer.valueOf(after);
            }
            
            // before range year
            for (int i = rangeBefore; i > 0; i--) {
                Map<String, Object> datum = new HashMap<>();
                int value = currYear - i;
                datum.put("value", value);
                datum.put("text", value);
                data.add(datum);
            }

            // current year, default select this year
            Map<String, Object> current = new HashMap<>();
            current.put("value", currYear);
            current.put("text", currYear);
            current.put("selected", "true");
            data.add(current);

            // after range year
            for (int i = 0; i < rangeAfter; i++) {
                Map<String, Object> datum = new HashMap<>();
                int value = currYear + i + 1;
                datum.put("value", value);
                datum.put("text", value);
                data.add(datum);
            }
        } catch (NumberFormatException e) {
            // current year
            Map<String, Object> current = new HashMap<>();
            current.put("value", currYear);
            current.put("text", currYear);
            current.put("selected", "true");
            data.add(current);
        }
        return data;
    }

	/**
	 *  清空redis缓存.
	 * @author gaojx
	 */
	@RequestMapping("clearCache")
	public Map<String, Object> clearRedisCache(){
	    Redis.redis.flushDB();
	    Map<String,Object> m = new HashMap<>();
	    m.put("statusCode", 200);
	    m.put("msg", "操作成功");
	    return m;
	}
	
}
