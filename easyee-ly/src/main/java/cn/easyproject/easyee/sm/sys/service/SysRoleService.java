package cn.easyproject.easyee.sm.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.entity.SysRole;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface SysRoleService extends BaseService<SysRole> {
    @Transactional(readOnly = true)
    public List<SysRole> list();
    @Transactional(readOnly = true)
    public boolean existsName(String name);
    @Transactional(readOnly = true)
    public boolean existsName(String name, Integer roleId);
    /**
     * 获得角色所有的权限Id集合（包括菜单权限和操作权限）
     * 
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, String>> getAllPermissionsIds(int roleId);

}
