package cn.easyproject.easyee.sm.sys.shiro;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;

import cn.easyproject.easyee.sm.base.util.JSONUtil;
import cn.easyproject.easyee.sm.base.util.SpringContextUtil;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sm.sys.entity.SysUser;
import cn.easyproject.easyee.sm.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.sm.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.sm.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.sm.sys.util.EasyUIUtil;
import cn.easyproject.easyshiro.EasyJdbcRealm;

/**
 * description: 刷新权限 <br/>
 * date: 2017年10月9日 上午9:26:40 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 */
public class RefreshPermission {

    /**
     * 刷新当前用户的菜单，修改菜单权限后，刷新主页即可更新菜单，无需重新登录
     */
    public static void refresh(HttpServletRequest request, SysUser sysUser) {
        if (null != sysUser) {
            HttpSession session = request.getSession();

            // 菜单权限集合
            session.setAttribute("menuTreeJson", getMenuStr(sysUser.getUserId()));

            // 保存所有权限对应的权限名称，权限备注
            session.setAttribute("operationsName", getOperationMap());

            // 权限重新加载
            EasyJdbcRealm.reloadPermissions();
        }
    }

    /**
     * 初始化菜单列表和操作权限
     * @param session
     * @param token
     */
    public static void initOnShiro(Session session, UsernamePasswordEncodeToken token) {

        // 菜单权限集合
        session.setAttribute("menuTreeJson", getMenuStr(token.getUserId()));

        // 保存所有权限对应的权限名称，权限备注
        session.setAttribute("operationsName", getOperationMap());
    }

    private static String getMenuStr(int userId) {
        SysMenuPermissionService sysMenuPermissionService = (SysMenuPermissionService) SpringContextUtil
                .getBeanByClass(SysMenuPermissionService.class);
        List<SysMenuPermission> menus = sysMenuPermissionService.listByUserId(userId);
        List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromUserMenuPermission(menus);
        return JSONUtil.beanToJson(list);
    }

    private static Map<String, String> getOperationMap() {
        // 保存所有权限对应的权限名称，权限备注
        SysOperationPermissionService sysOperationPermissionService = (SysOperationPermissionService) SpringContextUtil
                .getBeanByClass(SysOperationPermissionService.class);
        Map<String, String> operations = sysOperationPermissionService.getAllOpreationNames();
        return operations;
    }
}
