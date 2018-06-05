package cn.easyproject.easyee.sm.sys.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysLog;
import cn.easyproject.easyee.sm.sys.service.SysLogService;

@RestController
@RequestMapping("SysLog")
public class SysLogController extends BaseController<SysLog, SysLogCriteria> {
    public static Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Resource
    private SysLogService sysLogService;

    @Override
    public BaseService<SysLog> getService() {
        return sysLogService;
    }

    /**
     * 转向显示页面
     */
    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/sys/sysLog");
        return mv;
    }

    @Override
    public void beforeList(PageBean<SysLog> pb, SysLogCriteria c) {
        String sort = ServletRequestUtils.getStringParameter(request, "sort", "");
        String order = ServletRequestUtils.getStringParameter(request, "order", "");
        if (!StringUtil.isNotNullAndEmpty(sort) || sort.equals("logTime")) {
            sort = "log_Time";
        }
        if (!StringUtil.isNotNullAndEmpty(order)) {
            order = "desc";
        }
        pb.setSort(sort);
        pb.setSortOrder(order);
    }

}
