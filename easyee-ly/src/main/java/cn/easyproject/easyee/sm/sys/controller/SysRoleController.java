package cn.easyproject.easyee.sm.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.base.tool.StatusCode;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.criteria.SysRoleCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.sm.sys.entity.SysRole;
import cn.easyproject.easyee.sm.sys.service.SysRoleService;
import cn.easyproject.easyee.sm.sys.shiro.RefreshPermission;

/**
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("SysRole")
public class SysRoleController extends BaseController<SysRole, SysRoleCriteria> {
    public static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public BaseService<SysRole> getService() {
        return sysRoleService;
    }

    @Override
    public void afterList(PageBean<SysRole> pb, Map<Object, Object> m) {
        // 从分页集合中抽取需要输出的分页数据，不需要的不用输出，而且可以防止no session异常
        List<Map> list = EasyObjectExtract.extract(pb.getData(), new HashMap<String, String>(), "roleId", "name", "status", "remark");

        // 使用抽取的集合作为分页数据
        m.put("rows", list);
    }

    /**
     * 转向显示页面
     */
    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/sys/sysRole");
        return mv;
    }

    /**
     * 获得所有角色
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("all")
    public Map<Object, Object> all() throws Exception {
        return super.setJsonMap("list", sysRoleService.list());
    }

    /**
     * 获得所有权限的Id（菜单权限+操作权限），为了防止id重复，使用前缀标识
     */
    @RequestMapping("getAllPermissionsId")
    public Map<Object, Object> getAllPermissionsId(SysRole sysRole) {
        List<Map<String, String>> all = sysRoleService.getAllPermissionsIds(sysRole.getRoleId());
        List<String> allPermissions = new ArrayList<>();
        for (Map<String, String> res : all) {
            allPermissions.add(res.get("TYPE") + "_" + String.valueOf(res.get("ID")));
        }
        // System.out.println("###############################：：：" +
        // allPermissions);
        return super.setJsonMap("list", allPermissions);
    }

    /**
     * 添加角色
     */
    @Override
    public String save(SysRole sysRole) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        String menus = request.getParameter("menus");
        String operations = request.getParameter("operations");

        super.setMsgKey("msg.saveFail");
        super.setStatusCode(StatusCode.ERROR); // 默认为OK
        try {
            if (sysRoleService.existsName(sysRole.getName())) {
                super.setMsgKey("sys.RoleController.roleExists");
            } else {
                // 设置角色对应的菜单权限
                if (StringUtil.isNotNullAndEmpty(menus)) {
                    String[] menuIds = menus.split("#");
                    for (String menuId : menuIds) {
                        SysMenuPermission menuPermission = new SysMenuPermission();
                        menuPermission.setMenuPermissionId(Integer.valueOf(menuId.replace("menu_", "")));
                        sysRole.getSysMenuPermissions().add(menuPermission);
                    }
                }
                // 设置角色对应的操作权限
                if (StringUtil.isNotNullAndEmpty(operations)) {
                    String[] operationIds = operations.split("#");
                    for (String operationId : operationIds) {
                        SysOperationPermission operationPermission = new SysOperationPermission();
                        operationPermission.setOperationPermissionId(Integer.valueOf(operationId.replace("operation_", "")));
                        sysRole.getSysOperationPermissions().add(operationPermission);
                    }
                }

                sysRoleService.save(sysRole);
                super.setMsgKey("msg.saveSuccess");
                super.setStatusCode(StatusCode.OK);

                RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
            }
        } catch (Exception e) {
            logger.error(getText("sys.RoleController.saveException"), e);
            e.printStackTrace();
        }

        // 如果需要刷新，跳转到最后一页
        int maxPage = sysRoleService.findMaxPage(rows);
        return super.setJsonMsgStr("page", maxPage);
    }

    /**
     * 修改角色
     */
    @Override
    public String update(SysRole sysRole) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        String menus = request.getParameter("menus");
        String operations = request.getParameter("operations");

        super.setMsgKey("msg.updateFail");
        super.setStatusCode(StatusCode.ERROR); // 默认为OK
        try {

            if (sysRoleService.existsName(sysRole.getName(), sysRole.getRoleId())) {
                super.setMsgKey("sys.RoleController.roleExists");
            } else {
                SysRole sysRole2 = sysRoleService.get(sysRole.getRoleId());
                // 不修改用户角色关系
                sysRole.setSysUsers(sysRole2.getSysUsers());

                // 设置角色对应的菜单权限
                if (StringUtil.isNotNullAndEmpty(menus)) {
                    String[] menuIds = menus.split("#");
                    for (String menuId : menuIds) {
                        SysMenuPermission menuPermission = new SysMenuPermission();
                        menuPermission.setMenuPermissionId(Integer.valueOf(menuId.replace("menu_", "")));
                        sysRole.getSysMenuPermissions().add(menuPermission);
                    }
                }
                // 设置角色对应的操作权限
                if (StringUtil.isNotNullAndEmpty(operations)) {
                    String[] operationIds = operations.split("#");
                    for (String operationId : operationIds) {
                        SysOperationPermission operationPermission = new SysOperationPermission();
                        operationPermission.setOperationPermissionId(Integer.valueOf(operationId.replace("operation_", "")));
                        sysRole.getSysOperationPermissions().add(operationPermission);
                    }
                }

                sysRoleService.update(sysRole);
                super.setMsgKey("msg.updateSuccess");
                super.setStatusCode(StatusCode.OK);

                RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
            }
        } catch (Exception e) {
            logger.error(getText("sys.RoleController.updateException"), e);
            e.printStackTrace();
        }

        // 如果需要刷新，跳转到最后一页
        int maxPage = sysRoleService.findMaxPage(rows);
        return super.setJsonMsgStr("page", maxPage);
    }

    /**
     * 删除角色
     */
    @Override
    public Map<Object, Object> delete(SysRole sysRole) {
        try {
            sysRoleService.delete(sysRole);
            RefreshPermission.refresh(request, getLoginUser()); // 刷新权限
        } catch (Exception e) {
            e.printStackTrace();
            super.setStatusCode(StatusCode.ERROR); // 默认为OK
        }
        return super.setJsonMsgMap();
    }

}
