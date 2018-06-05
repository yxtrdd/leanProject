package cn.easyproject.easyee.sm.hr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.hr.dao.EmpDAO;
import cn.easyproject.easyee.sm.hr.entity.Emp;
import cn.easyproject.easyee.sm.hr.service.EmpService;

/**
 * 业务实现类统一继承 BaseService 类
 * @author easyproject.cn
 * @version 1.0
 */
@Service("empService")
public class EmpServiceImpl extends BaseServiceImpl<Emp> implements EmpService {

    @Resource
    private EmpDAO empDAO;

    @Override
    public BaseDAO<Emp> getDao() {
        return empDAO;
    }

    @Override
    public void findByPage(PageBean<Emp> pageBean, EasyCriteria empCriteria) {
        pageBean.setFrom(" module_emp e, module_dept d ");
        pageBean.setSelect("e.empno, e.ename, e.job, d.deptno, d.dname");
        pageBean.addCondition("and e.deptno=d.deptno");
        pageBean.setPrimaryTable("e");
        pageBean.setEasyCriteria(empCriteria);
        // 按条件分页查询
        empDAO.pagination(pageBean);
    }

    @Override
    public int findEmpCountByDeptno(int deptno) {
        return empDAO.findEmpCountByDeptno(deptno);
    }

}
