package cn.easyproject.easyee.sm.hr.criteria;

import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.util.StringUtil;

/**
 * ModuleEmp entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class EmpCriteria extends EasyCriteria implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
 	 * 1. 条件属性
 	 */
	private Integer empno;
	private Integer deptno;
	private String ename;
	private String job;
	
	/*
 	 * 2. 构造方法
 	 */
	 public EmpCriteria() {
		super();
	}



	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	@Override
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if(StringUtil.isNotNullAndEmpty(this.getEname())){
			condition.append(" and ename like #{ename}");
			values.put("ename", "%"+this.getEname()+"%");
		}
		if(StringUtil.isNotNullAndEmpty(this.getEmpno())){
			condition.append(" and empno=#{empno}");
			values.put("empno",this.getEmpno());
		}
		if(StringUtil.isNotNullAndEmpty(this.getDeptno())){
			condition.append(" and d.deptno=#{deptno}");
			values.put("deptno",this.getDeptno());
		}
		if(StringUtil.isNotNullAndEmpty(this.getJob())){
			condition.append(" and job like #{job}");
			values.put("job", "%"+this.getJob()+"%");
		}
		return condition.toString();
	}
	
	 /*
 	 * 4. Setters & Getters...
 	 */ 
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	
	public Integer getDeptno() {
		return deptno;
	}



	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}



	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}


}