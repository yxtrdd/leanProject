package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;

/**
 * @author easyproject.cn
 * @version 1.0
 */
public interface SysMenuPermissionDAO extends BaseDAO<SysMenuPermission> {
    // delete sys_role_menu
    public void deleteMenuRoles(SysMenuPermission sysMenuPermission);
    // delete sys_role_operation
    public void deleteMenuOperationRoles(SysMenuPermission sysMenuPermission);
    // delete sys_operation
    public void deleteMenuOperation(SysMenuPermission sysMenuPermission);

    public List<SysMenuPermission> list();
    public List<Map<String, String>> listAll();
    public int getMaxSortNum(@Param("parentId") Integer parentId);
    public int getSortNum(Integer menuPermissionId);
    public int hashChildMenu(Integer menuPermissionId);

    public List<String> getIdsByRoleId(Integer roleId);

    public SysMenuPermission getUpNeighbor(SysMenuPermission sysMenuPermission);
    public SysMenuPermission getDownNeighbor(SysMenuPermission sysMenuPermission);
    public void move(SysMenuPermission sysMenuPermission);

    public List<SysMenuPermission> listByUserId(int userId);
}
