package cn.easyproject.easyee.sm.hr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.hr.dao.DeptDAO;
import cn.easyproject.easyee.sm.hr.entity.Dept;
import cn.easyproject.easyee.sm.hr.service.DeptService;

/**
 * 业务实现类统一继承 BaseService 类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    @Resource
    private DeptDAO deptDAO;

    @Override
    public BaseDAO<Dept> getDao() {
        return deptDAO;
    }

    @Override
    public void findByPage(PageBean<Dept> pageBean, EasyCriteria deptCriteria) {
        pageBean.setFrom("module_dept dept");
        pageBean.setSelect("dept.*");
        pageBean.setEasyCriteria(deptCriteria);

        // 按条件分页查询
        deptDAO.pagination(pageBean);
    }

}
