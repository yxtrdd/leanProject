package cn.easyproject.easyee.sm.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface SysOperationPermissionService extends BaseService<SysOperationPermission> {

    public void deleteByMenuPermissionId(int menuPermissionId);
    public void delete(SysOperationPermission sysOperationPermission);

    @Transactional(readOnly = true)
    public List<Map<?, ?>> list(Integer menuId);
    
    /**
     * 查询角色的所有操作权限ID
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> getIdsByRoleId(Integer roleId);
    
    /**
     * 获得权限动作和名称备注的映射，包括Menu和Operation权限
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, String> getAllOpreationNames();
}
