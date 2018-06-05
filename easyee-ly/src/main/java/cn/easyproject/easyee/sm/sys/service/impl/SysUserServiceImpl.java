package cn.easyproject.easyee.sm.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.dao.SysUserDAO;
import cn.easyproject.easyee.sm.sys.entity.SysUser;
import cn.easyproject.easyee.sm.sys.service.SysUserService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserDAO sysUserDAO;

    @Override
    public BaseDAO<SysUser> getDao() {
        return sysUserDAO;
    }

    @Override
    public void save(SysUser sysuser) {
        sysUserDAO.save(sysuser);
        // 添加角色
        if (sysuser.getSysRoles().size() > 0) {
            sysUserDAO.addUserRoles(sysuser);
        }
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            sysUserDAO.deleteUserRoles(id);
        }
        sysUserDAO.deleteByIds(ids);
    }

    @Override
    public void update(SysUser sysUser) {
        // 删除用户旧角色
        sysUserDAO.deleteUserRoles(sysUser.getUserId());
        // 更新用户
        sysUserDAO.update(sysUser);
        // 添加新角色
        if (sysUser.getSysRoles().size() > 0) {
            sysUserDAO.addUserRoles(sysUser);
        }

    }

    @Override
    public void findByPage(PageBean<SysUser> pb, EasyCriteria sysUserCriteria) {
        pb.setEasyCriteria(sysUserCriteria);
        String condition = "";

        String sort = "USER_ID";
        String sortOrder = "ASC";

        if (StringUtil.isNotNullAndEmpty(pb.getSort())) {
            sort = pb.getSort();
        }
        if (StringUtil.isNotNullAndEmpty(pb.getSortOrder())) {
            sortOrder = pb.getSortOrder();
        }

        if (pb.getEasyCriteria() != null) {
            condition = pb.getEasyCriteria().getCondition();
            pb.setSqlParameterValues(pb.getEasyCriteria().getValues());
        }
        if (pb.getDialect() == PageBean.MYSQL_DIALECT) {
            pb.setSql("select A.* from (" + "SELECT s.*,r.role_id AS ROLE_ID,r.name AS ROLE_NAME "
                    + " FROM Sys_User s, SYS_USER_ROLE ur, sys_role r WHERE 1=1 AND s.user_id=ur.user_id AND r.role_id=ur.role_id " + " UNION"
                    + " SELECT s.*,-1,'' FROM Sys_User s WHERE NOT EXISTS(SELECT 1 FROM sys_user_role ur WHERE ur.USER_ID=s.USER_ID)" + " ) A"
                    + " WHERE 1=1 " + condition + " order by A." + sort + " " + sortOrder + " limit " + ((pb.getPageNo() - 1) * pb.getRowsPerPage())
                    + "," + pb.getRowsPerPage());
        } else if (pb.getDialect() == PageBean.ORACLE_DIALECT || pb.getDialect() == PageBean.ORACLE_12C_DIALECT) {
            int start = ((pb.getPageNo() - 1) * pb.getRowsPerPage());
            int end = start + pb.getRowsPerPage();
            String sql = "select A.* from (" + "SELECT s.*,r.role_id AS ROLE_ID,r.name AS ROLE_NAME "
                    + " FROM Sys_User s, SYS_USER_ROLE ur, sys_role r WHERE 1=1 AND s.user_id=ur.user_id AND r.role_id=ur.role_id " + " UNION"
                    + " SELECT s.*,-1,'' FROM Sys_User s WHERE NOT EXISTS(SELECT 1 FROM sys_user_role ur WHERE ur.USER_ID=s.USER_ID)" + " ) A"
                    + " WHERE 1=1 " + condition + " order by A." + sort + " " + sortOrder;

            sql = "select * from (" + "select B.*,rownum r from (" + sql + ") B where rownum<=" + end + ") where r>" + start;

            pb.setSql(sql);

        }

        pb.setCountSQL("SELECT COUNT(*) FROM (" + " SELECT s.*,r.role_id AS ROLE_ID,r.name AS ROLE_NAME "
                + " FROM Sys_User s, SYS_USER_ROLE ur, sys_role r WHERE 1=1 AND s.user_id=ur.user_id AND r.role_id=ur.role_id " + " UNION"
                + " SELECT s.*,-1,'' FROM Sys_User s WHERE NOT EXISTS(SELECT 1 FROM sys_user_role ur WHERE ur.USER_ID=s.USER_ID)" + " ) A"
                + " WHERE 1=1 " + condition);

        // 按条件分页查询
        sysUserDAO.pagination(pb);
    }

    @Override
    public SysUser login(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        // 用户状态：0启用；1禁用；2删除
        SysUser user = sysUserDAO.login(sysUser);
        return user;
    }

    @Override
    public void changePwd(int id, String pwd) {
        sysUserDAO.changePwd(id, pwd);
    }

    @Override
    public boolean existsName(String name) {
        return sysUserDAO.existsName(name) > 0;
    }
    @Override
    public boolean existsName(String name, Integer userId) {
        // 修改用户时，检测用户名是否存在
        return sysUserDAO.existsNameWhenEdit(name, userId) > 0;
    }

    @Override
    public String getPwd(int id) {
        return sysUserDAO.getPwd(id);
    }

    @Override
    public void delete(SysUser sysUser) {
        // 删除用户旧角色
        sysUserDAO.deleteUserRoles(sysUser.getUserId());
        sysUserDAO.delete(sysUser);
    }

}
