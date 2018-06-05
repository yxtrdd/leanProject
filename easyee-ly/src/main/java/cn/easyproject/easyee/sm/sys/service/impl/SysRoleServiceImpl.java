package cn.easyproject.easyee.sm.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.sys.dao.SysRoleDAO;
import cn.easyproject.easyee.sm.sys.entity.SysRole;
import cn.easyproject.easyee.sm.sys.service.SysRoleService;

/**
 * @author easyproject.cn
 * @version 1.0
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Resource
    private SysRoleDAO sysRoleDAO;

    @Override
    public BaseDAO<SysRole> getDao() {
        return sysRoleDAO;
    }

    @Override
    public void save(SysRole sysRole) {
        sysRoleDAO.save(sysRole);
        if (sysRole.getSysMenuPermissions().size() > 0) {
            sysRoleDAO.addRoleMenus(sysRole);
        }
        if (sysRole.getSysOperationPermissions().size() > 0) {
            sysRoleDAO.addRoleOperations(sysRole);
        }
    }

    @Override
    public void delete(SysRole sysRole) {
        sysRoleDAO.deleteRoleMenus(sysRole.getRoleId());
        sysRoleDAO.deleteRoleOperations(sysRole.getRoleId());
        sysRoleDAO.deleteRoleUsers(sysRole.getRoleId());
        sysRoleDAO.delete(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        sysRoleDAO.deleteRoleMenus(sysRole.getRoleId());
        sysRoleDAO.deleteRoleOperations(sysRole.getRoleId());
        sysRoleDAO.update(sysRole);
        if (sysRole.getSysMenuPermissions().size() > 0) {
            sysRoleDAO.addRoleMenus(sysRole);
        }
        if (sysRole.getSysOperationPermissions().size() > 0) {
            sysRoleDAO.addRoleOperations(sysRole);
        }
    }

    @Override
    public List<SysRole> list() {
        return sysRoleDAO.list();
    }

    @Override
    public void findByPage(PageBean<SysRole> pb, EasyCriteria sysRoleCriteria) {
        pb.setFrom("Sys_Role s");
        pb.setSelect("s.*");
        pb.setEasyCriteria(sysRoleCriteria);

        // 按条件分页查询
        sysRoleDAO.pagination(pb);
    }

    @Override
    public List<Map<String, String>> getAllPermissionsIds(int roleId) {
        // 查询角色的菜单权限Id和操作权限Id
        // 返回所有权限id
        return sysRoleDAO.getAllPermissionsIds(roleId);
    }
    
    @Override
    public boolean existsName(String name) {
        return sysRoleDAO.existsName(name) > 0;
    }
    
    @Override
    public boolean existsName(String name, Integer roleId) {
        // 修改用户时，检测用户名是否存在
        return sysRoleDAO.existsNameWhenEdit(name, roleId) > 0;
    }

}
