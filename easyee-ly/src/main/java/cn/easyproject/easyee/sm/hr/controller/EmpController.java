package cn.easyproject.easyee.sm.hr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.sm.hr.entity.Emp;
import cn.easyproject.easyee.sm.hr.service.DeptService;
import cn.easyproject.easyee.sm.hr.service.EmpService;

/**
 * 所有Controller处理类统一继承BaseController
 * 
 * BaseController中定义了一下内容： - request, application Servlet API -
 * 请求响应相关的JSON参数（EasyUI框架请求都是通过JSON响应） -
 * 初始化JSON响应数据的方法（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean,
 * Object...)） - 获得分页对象：super.getPageBean()
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("Emp")
public class EmpController extends BaseController<Emp, EmpCriteria> {
    public static Logger logger = LoggerFactory.getLogger(EmpController.class);

    @Resource
    private EmpService empService;
    @Resource
    private DeptService deptService;

    @Override
    public BaseService<Emp> getService() {
        return empService;
    }

    @Override
    public void afterList(PageBean<Emp> pb, Map<Object, Object> m) {
        super.afterList(pb, m);
        m.put("allDept", deptService.findAll());
    }

    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/hr/Emp");
        return mv;
    }

    /**
     * 查询所有部门，以JSON返回，添加修改用户时，下拉列表使用
     */
    @RequestMapping("allDept")
    public Object allDept() {
        return deptService.findAll();
    }

}
