package cn.easyproject.easyee.sm.sys.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转
 * @author easyproject.cn
 * @version 1.0
 */
@Controller
public class ForwardController {

    @Resource
    protected HttpServletRequest request;
    
	@RequestMapping("toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("toMain")
	public String toMain() {
		return "main/main";
	}

	@RequestMapping("toReports")
	public String toReports() {
		return "main/reports/doChart";
	}

	/**
	 * 页面加载公共实现（三选一）
	 * @author gaojx  
	 * @param mv
	 * @param forward  服务器内部重定向（建议优先使用，避免暴露资源路径）
	 * @param redirect 客户端重定向
	 * @param target   直接指定资源
	 * @return
	 */
	@RequestMapping("toForward")
    public ModelAndView toForward(ModelAndView mv) {
        Map<String, Object> param = this.getParameterMap();
        mv.addAllObjects(param);
        if (param.containsKey("forward")) { // 服务器内部重定向
            mv.setViewName("forward:/" + String.valueOf(param.get("forward")));
        } else if (param.containsKey("redirect")) { //客户端重定向
            mv.setViewName("redirect:/" + String.valueOf(param.get("redirect")));
        } else if (param.containsKey("target")) {   // 直接指定资源
            mv.setViewName(String.valueOf(param.get("target")));
        } else {
            mv.setViewName("error/actionError");
        }
        return mv;
    }

	 /**
     * getParameterMap:获取所有请求参数转换为Map <br/>
     * 当传递的参数个数不固定且参数名没有重复时使用 <br/>
     * 注意：参数名称若有重复的只能得到第一个 <br/>
     */
    protected Map<String, Object> getParameterMap() {
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

}
