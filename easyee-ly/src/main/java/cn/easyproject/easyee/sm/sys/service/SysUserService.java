package cn.easyproject.easyee.sm.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.entity.SysUser;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface SysUserService extends BaseService<SysUser> {
    
    public SysUser login(SysUser sysUser);
    
    public void changePwd(int id, String pwd);
    
    @Transactional(readOnly = true)
    public boolean existsName(String name);
    
    @Transactional(readOnly = true)
    public boolean existsName(String name, Integer userId);
    
    @Transactional(readOnly = true)
    public String getPwd(int id);
}
