package cn.easyproject.easyee.sm.sys.controller;

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
import cn.easyproject.easyee.sm.sys.criteria.SysMenuPermissionCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sm.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.sm.sys.shiro.RefreshPermission;
import cn.easyproject.easyee.sm.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.sm.sys.util.EasyUIUtil;

/**
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("SysMenuPermission")
public class SysMenuPermissionController extends BaseController<SysMenuPermission, SysMenuPermissionCriteria> {

    public static Logger logger = LoggerFactory.getLogger(SysMenuPermissionController.class);

    @Resource
    private SysMenuPermissionService sysMenuPermissionService;

    @Override
    public BaseService<SysMenuPermission> getService() {
        return sysMenuPermissionService;
    }

    /**
     * 转向显示页面
     */
    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/sys/sysMenuPermission");
        return mv;
    }

    @Override
    public String save(SysMenuPermission sysMenuPermission) {
        super.setMsgKey("msg.saveSuccess");
        try {
            // 设置排序位置为最大
            sysMenuPermission.setSortNum(sysMenuPermissionService.getMaxSortNum(sysMenuPermission.getSysMenuPermission().getMenuPermissionId()) + 1);
            // 添加根节点
            if (sysMenuPermission.getSysMenuPermission().getMenuPermissionId() == -1) {
                sysMenuPermission.setSysMenuPermission(null);
            }
            sysMenuPermissionService.save(sysMenuPermission);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getText("sys.MenuPermissionController.saveException"), e);
            super.setMsgKey("msg.saveFail");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgStr();
    }

    /**
     * 更新
     */
    @Override
    public String update(SysMenuPermission sysMenuPermission) {
        super.setMsgKey("msg.updateSuccess");
        try {
            SysMenuPermission sysMenuPermissionOld = sysMenuPermissionService.get(sysMenuPermission.getMenuPermissionId());

            // 排序位置不变
            sysMenuPermission.setSortNum(sysMenuPermissionOld.getSortNum());
            // 角色信息不变
            sysMenuPermission.setSysRoles(sysMenuPermissionOld.getSysRoles());

            if (sysMenuPermission.getSysMenuPermission() != null && sysMenuPermission.getSysMenuPermission().getMenuPermissionId() == null) {
                sysMenuPermission.setSysMenuPermission(null);
            }
            sysMenuPermissionService.update(sysMenuPermission);
            RefreshPermission.refresh(request, getLoginUser()); // 自动刷新菜单权限
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getText("sys.MenuPermissionController.updateException"), e);
            super.setMsgKey("msg.updateFail");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgStr();
    }

    /**
     * 移动
     */
    @RequestMapping("move")
    public Map<Object, Object> move(SysMenuPermission sysMenuPermission, boolean up) throws Exception {
        super.setMsgKey("msg.moveSuccess");
        try {
            sysMenuPermissionService.move(sysMenuPermission.getMenuPermissionId(), up);
            RefreshPermission.refresh(request, getLoginUser()); // 自动刷新菜单权限
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getText("sys.MenuPermissionController.moveException"), e);
            super.setMsgKey("msg.moveFail");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgMap();
    }

    /**
     * 删除
     */
    @Override
    public Map<Object, Object> delete(SysMenuPermission sysMenuPermission) {
        super.setMsgKey("msg.deleteSuccess");
        try {
            sysMenuPermission = sysMenuPermissionService.get(sysMenuPermission.getMenuPermissionId());
            if (sysMenuPermissionService.hashChildMenu(sysMenuPermission.getMenuPermissionId())) {
                super.setMsgKey("sys.MenuPermissionController.deleteMsg");
                super.setStatusCode(StatusCode.ERROR); // 默认为OK
            } else {
                sysMenuPermissionService.delete(sysMenuPermission);
                RefreshPermission.refresh(request, getLoginUser()); // 自动刷新菜单权限
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getText("sys.MenuPermissionController.deleteException"), e);
            super.setMsgKey("sys.MenuPermissionController.deleteException");
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgMap();
    }

    /**
     * 获得菜单权限，单个根节点
     */
    @Override
    public String list(SysMenuPermissionCriteria c) {
        List<SysMenuPermission> menus = sysMenuPermissionService.list();
        List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromRootMenu(menus);
        return toJson(list);
    }

    /**
     * 针对角色加载权限，获得菜单权限和操作权限 <br/>
     * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("listAllForSysRole")
    public String listAllForSysRole() throws Exception {
        List menus = sysMenuPermissionService.listAll();
        List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeUsePrefixIdFromMenuAndOperation(menus);
        return toJson(list);
    }

    /**
     * 获得菜单权限，允许多个根节点，根节点ID为-1
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("list2")
    public Object list2() throws Exception {
        List<SysMenuPermission> menus = sysMenuPermissionService.list();
        List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromRootMenu(menus);
        list.add(0, new EasyUITreeEntity("-1", "根节点"));
        return list;
    }

}
