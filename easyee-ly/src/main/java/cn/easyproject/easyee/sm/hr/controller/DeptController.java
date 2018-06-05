package cn.easyproject.easyee.sm.hr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.base.tool.StatusCode;
import cn.easyproject.easyee.sm.hr.criteria.DeptCriteria;
import cn.easyproject.easyee.sm.hr.entity.Dept;
import cn.easyproject.easyee.sm.hr.service.DeptService;
import cn.easyproject.easyee.sm.hr.service.EmpService;

/**
 * 所有Controller处理类统一继承BaseController
 * 
 * BaseController中定义了一下内容： - request, application Servlet API -
 * 请求响应相关的JSON参数（EasyUI框架请求都是通过JSON响应） -
 * 初始化JSON响应数据的方法（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean,
 * Object...)） - 获得分页对象：super.getPageBean()
 * 
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("Dept")
public class DeptController extends BaseController<Dept, DeptCriteria> {
    public static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Resource
    private DeptService deptService;

    @Resource
    private EmpService empService;

    @Override
    public BaseService<Dept> getService() {
        return deptService;
    }

    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/hr/Dept");
        return mv;
    }

    @Override
    public Map<Object, Object> delete(Dept dept) {
        try {
            if (empService.findEmpCountByDeptno(dept.getDeptno()) == 0) {
                deptService.delete(dept);
            } else {
                super.setMsgKey("hr.DeptController.empExists");
                super.setStatusCode(StatusCode.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgMap();
    }

}
