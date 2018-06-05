package cn.easyproject.easyee.sm.sys.dao;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.sys.entity.SysUser;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysUserDAO extends BaseDAO<SysUser> {

    public SysUser login(SysUser sysUser);

    public void addUserRoles(SysUser sysuser);
    public void deleteUserRoles(Serializable userId);

    public void changePwd(@Param("userId") int id, @Param("password") String pwd);
    public int existsName(String name);
    public int existsNameWhenEdit(@Param("name") String name, @Param("userId") Integer userId);
    public String getPwd(int id);
}
