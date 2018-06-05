package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;

/**
 * @author easyproject.cn
 * @version 1.0
 */
public interface SysOperationPermissionDAO extends BaseDAO<SysOperationPermission> {
    public void deleteOperationRoles(SysOperationPermission sysOperationPermission);
    public void deleteOperationRolesByIds(String[] ids);
    public void deleteByMenuPermissionId(int menuPermissionId);

    public List<Map<?, ?>> list(Integer menuId);
    /**
     * 查询角色的所有操作权限ID
     * 
     * @param roleId
     * @return
     */
    public List<String> getIdsByRoleId(Integer roleId);
    /**
     * 获得权限动作和名称备注的映射，包括Menu和Operation权限
     * 
     * @return
     */
    public List<Map<String, String>> getAllOpreationNames();
}
