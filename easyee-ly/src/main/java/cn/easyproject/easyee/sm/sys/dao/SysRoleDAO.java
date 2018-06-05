package cn.easyproject.easyee.sm.sys.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.sys.entity.SysRole;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysRoleDAO extends BaseDAO<SysRole> {
    public void addRoleMenus(SysRole SysRole);
    public void addRoleOperations(SysRole SysRole);

    public void deleteRoleMenus(Serializable roleId);
    public void deleteRoleOperations(Serializable roleId);
    public void deleteRoleUsers(Serializable roleId);

    public List<SysRole> list();

    public int existsName(String name);
    public int existsNameWhenEdit(@Param("name") String name, @Param("roleId") Integer roleId);
    /**
     * 获得角色所有的权限Id集合（包括菜单权限和操作权限）
     * 
     * @param roleId
     * @return
     */
    public List<Map<String, String>> getAllPermissionsIds(int roleId);

}
