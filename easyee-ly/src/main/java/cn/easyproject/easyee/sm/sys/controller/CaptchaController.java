package cn.easyproject.easyee.sm.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.easyproject.easyee.sm.base.tool.StatusCode;

/**
 * 验证码检测
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@RestController
public class CaptchaController {
    public static Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    /**
     * 验证码检测
     */
    @RequestMapping("checkCaptcha")
    public Map<Object, Object> checkCaptcha(HttpServletRequest request, String captcha) {
        Map<Object, Object> m = new HashMap<Object, Object>();
        m.put("statusCode", StatusCode.OK);
        Object sessionCaptcha = request.getSession().getAttribute("rand");
        if (sessionCaptcha == null || !sessionCaptcha.toString().equalsIgnoreCase(captcha)) {
            m.put("statusCode", StatusCode.ERROR);
            m.put("msg", "验证码错误");
        }
        return m;
    }

}
