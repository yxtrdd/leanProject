package cn.easyproject.easyee.sm.hr.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.hr.entity.Emp;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface EmpService extends BaseService<Emp> {

    /**
     * Max Page
     * @param rowPerPage Row Per Page
     * @return maxPage
     */
    @Transactional(readOnly = true)
    public int findEmpCountByDeptno(int deptno);
}
