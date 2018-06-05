package cn.easyproject.easyee.sm.sys.controller;

import java.util.HashMap;
import java.util.List;
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
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.criteria.SysOperationPermissionCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.sm.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.sm.sys.shiro.RefreshPermission;

/**
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("SysOperationPermission")
public class SysOperationPermissionController extends BaseController<SysOperationPermission, SysOperationPermissionCriteria> {
    public static Logger logger = LoggerFactory.getLogger(SysOperationPermissionController.class);

    @Resource
    private SysOperationPermissionService sysOperationPermissionService;

    @Override
    public BaseService<SysOperationPermission> getService() {
        return sysOperationPermissionService;
    }

    /**
     * 转向显示页面
     */
    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/sys/sysOperationPermission");
        return mv;
    }

    @Override
    public String list(SysOperationPermissionCriteria c) {
        String menuId = c.getMenuId();
        Object jsonRoot = new Object[]{};
        if (StringUtil.isNotNullAndEmpty(menuId)) {
            List<Map<?, ?>> list = sysOperationPermissionService.list(Integer.valueOf(menuId));
            jsonRoot = setJsonMap("rows", list, "sysMenuPermission.menuPermissionId", menuId);
        }
        return toJson(jsonRoot);
    }

    @Override
    public String update(SysOperationPermission sysOperationPermission) {
        try {
            sysOperationPermissionService.update(sysOperationPermission);
            super.setMsgKey("msg.updateSuccess");
            RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
        } catch (Exception e) {
            logger.error(getText("sys.OperationPermissionController.updateException"), e);
            e.printStackTrace();
            super.setMsgKey("msg.updateFail");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgStr();
    }

    @Override
    public String save(SysOperationPermission sysOperationPermission) {
        try {
            sysOperationPermissionService.save(sysOperationPermission);
            super.setMsgKey("msg.saveSuccess");
            RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
        } catch (Exception e) {
            logger.error(getText("sys.OperationPermissionController.saveException"), e);
            e.printStackTrace();
            super.setMsgKey("msg.saveFail");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        // System.out.println(sysOperationPermission.getOperationPermissionId());
        // 返回添加的数据主键信息
        Map<String, Integer> rowData = new HashMap<>();
        rowData.put("operationPermissionId", sysOperationPermission.getOperationPermissionId());
        return super.setJsonMsgStr("rowData", rowData);
    }

    @Override
    public Map<Object, Object> delete(SysOperationPermission sysOperationPermission) {
        try {
            sysOperationPermissionService.delete(sysOperationPermissionService.get(sysOperationPermission.getOperationPermissionId()));
            super.setMsgKey("msg.deleteSuccess");
            RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
        } catch (Exception e) {
            logger.error(getText("sys.OperationPermissionController.deleteException"), e);
            e.printStackTrace();
            super.setMsgKey("msg.deleteSuccess");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgMap();
    }

}
