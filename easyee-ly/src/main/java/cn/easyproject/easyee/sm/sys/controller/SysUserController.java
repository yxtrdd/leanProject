package cn.easyproject.easyee.sm.sys.controller;

import java.lang.reflect.InvocationTargetException;
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
import cn.easyproject.easyee.sm.base.tool.MD5;
import cn.easyproject.easyee.sm.base.tool.StatusCode;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysRole;
import cn.easyproject.easyee.sm.sys.entity.SysUser;
import cn.easyproject.easyee.sm.sys.service.SysUserService;
import cn.easyproject.easyee.sm.sys.shiro.RefreshPermission;

/**
 * @author easyproject.cn
 * @version 1.0
 */
@RestController
@RequestMapping("SysUser")
public class SysUserController extends BaseController<SysUser, SysUserCriteria> {

    public static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private SysUserService sysUserService;

    @Override
    public BaseService<SysUser> getService() {
        return sysUserService;
    }

    @RequestMapping("page")
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName("main/sys/sysUser");
        return mv;
    }

    @Override
    public void beforeList(PageBean<SysUser> pb, SysUserCriteria c) {
        if ("realName".equals(pb.getSort())) {
            pb.setSort("real_name");
        }
        if (!StringUtil.isNotNullAndEmpty(pb.getSort())) {
            pb.setSort("USER_ID");
        }
    }

    @Override
    public void afterList(PageBean<SysUser> pb, Map<Object, Object> m) {
        List<Map> list = EasyObjectExtract.extract(pb.getData(), "userId", "realName", "name", "status", "{sysRoles}.name#roleNames",
                "{sysRoles}.roleId#roleIds");
        // 使用抽取的集合作为分页数据
        m.put("rows", list);
    }

    @RequestMapping(value = "/changePwd", produces = "text/html;charset=UTF-8")
    public String changePwd(String newPwd, String confirmPwd, SysUser sysUser)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Object u = request.getSession().getAttribute("USER");

        super.setMsgKey("sys.UserController.pwdFail");

        if (u != null) {
            String name = "";
            String password = "";
            int userId = 0;
            SysUser u2 = null;
            if (u instanceof SysUser) {
                u2 = (SysUser) u;
            } else {
                name = u.getClass().getMethod("getName").invoke(u).toString();
                password = u.getClass().getMethod("getPassword").invoke(u).toString();
                userId = Integer.valueOf(u.getClass().getMethod("getUserId").invoke(u).toString());
            }
            String originalPwd = MD5.getMd5(sysUser.getPassword(), name.toLowerCase());

            // 旧密码正确
            if (password.equals(originalPwd)) {

                if (confirmPwd != null && confirmPwd.equals(newPwd)) {
                    try {
                        String newpwd2 = MD5.getMd5(newPwd, name.toLowerCase());
                        sysUserService.changePwd(userId, newpwd2);
                        super.setMsgKey("msg.updateSuccess");

                        // 修改session中的用户密码
                        if (u2 == null) {
                            u.getClass().getMethod("setPassword").invoke(u, newpwd2);
                        } else {
                            u2.setPassword(newpwd2);
                        }
                    } catch (Exception e) {
                        super.setStatusCode(StatusCode.ERROR); // 默认为OK
                        logger.error(getText("sys.UserController.pwdException"), e);
                    }
                } else {
                    super.setStatusCode(StatusCode.ERROR); // 默认为OK
                    super.setMsgKey("sys.UserController.pwdNotEqauals");
                }
            } else {
                super.setStatusCode(StatusCode.ERROR); // 默认为OK
                super.setMsgKey("sys.UserController.pwdWrongError");
            }
        }
        return super.setJsonMsgStr();
    }

    @Override
    public String save(SysUser sysUser) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        String confirmPwd = request.getParameter("confirmPwd");
        String[] roleIds = request.getParameterValues("roleIds");

        super.setStatusCode(StatusCode.ERROR); // 默认为OK
        super.setMsgKey("msg.saveFail");
        if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {

            if (sysUserService.existsName(sysUser.getName())) {
                super.setMsgKey("sys.UserController.userExists");
            } else {
                if (roleIds != null) {
                    for (String roleId : roleIds) { // 保存角色
                        sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
                    }
                }
                // 保存用户
                try {
                    sysUser.setPassword(MD5.getMd5(sysUser.getPassword(), sysUser.getName().toLowerCase()));
                    sysUserService.save(sysUser);
                    super.setMsgKey("msg.saveSuccess");
                    super.setStatusCode(StatusCode.OK);
                } catch (Exception e) {
                    logger.error(getText("sys.UserController.saveException"), e);
                }
            }

        } else {
            super.setMsgKey("sys.UserController.pwdNotEqauals");
        }

        // 如果需要刷新，跳转到最后一页
        int maxPage = sysUserService.findMaxPage(rows);
        return super.setJsonMsgStr("page", maxPage);
    }

    @Override
    public String update(SysUser sysUser) {
        String confirmPwd = request.getParameter("confirmPwd");
        String[] roleIds = request.getParameterValues("roleIds");

        super.setMsgKey("msg.updateFail");
        super.setStatusCode(StatusCode.ERROR); // 默认为OK
        if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {
            try {
                if (sysUserService.existsName(sysUser.getName(), sysUser.getUserId())) {
                    super.setMsgKey("sys.UserController.userExists");
                } else {
                    String oldPwd = sysUserService.getPwd(sysUser.getUserId());
                    if (roleIds != null) {
                        for (String roleId : roleIds) { // 保存角色
                            sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
                        }
                    }
                    // 判断密码是否修改了，没有修改则使用旧密码
                    if (!StringUtil.isNotNullAndEmpty(sysUser.getPassword())) {
                        sysUser.setPassword(oldPwd);
                    } else {
                        sysUser.setPassword(MD5.getMd5(sysUser.getPassword(), sysUser.getName().toLowerCase()));
                    }

                    // 保存用户
                    sysUserService.update(sysUser);

                    // 如果修改了当前登录用户，则修改session中的用户密码
                    // SysUser u = (SysUser)
                    // request.getSession().getAttribute("USER");
                    Object u = request.getSession().getAttribute("USER");

                    if (u instanceof SysUser) {
                        SysUser u2 = (SysUser) request.getSession().getAttribute("USER");
                        u2.setPassword(sysUser.getPassword());
                    } else {
                        int userId = Integer.valueOf(u.getClass().getMethod("getUserId").invoke(u).toString());
                        if (userId == sysUser.getUserId().intValue()) {
                            u.getClass().getMethod("setPassword").invoke(u, sysUser.getPassword());
                        }
                    }
                    super.setMsgKey("msg.updateSuccess");
                    super.setStatusCode(StatusCode.OK);

                    // 如果修改的是当前用户自己，则刷新其菜单权限和操作权限
                    if (sysUser.getUserId() == getLoginUser().getUserId()) {
                        RefreshPermission.refresh(request, getLoginUser()); // 自动刷新权限
                    }
                    // 修改刷新当前页
                    // super.page=sysUserService.findMaxPage(rows);
                }
            } catch (Exception e) {
                logger.error(getText("sys.UserController.updateException"), e);
            }

        } else {
            super.setMsgKey("sys.UserController.pwdNotEqauals");
        }
        return super.setJsonMsgStr();

    }

    @Override
    public Map<Object, Object> deleteBatch(String[] userId) {
        return super.deleteBatch(userId);
    }

}
