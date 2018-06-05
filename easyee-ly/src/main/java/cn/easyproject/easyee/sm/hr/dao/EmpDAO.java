package cn.easyproject.easyee.sm.hr.dao;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.hr.entity.Emp;

/**
 * @author easyproject.cn
 * @version 1.0
 */
public interface EmpDAO extends BaseDAO<Emp> {
	
	/**
	 * Max Page
	 * @param rowPerPage Row Per Page
	 * @return maxPage
	 */
	public int findEmpCountByDeptno(int deptno);
	
}
