package cn.easyproject.easyee.sm.sys.criteria;


import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.util.StringUtil;

/**
 * SysRole entity.
 * 
 * @author easyproject.cn
 * @version 1.0
 */
public class SysRoleCriteria extends EasyCriteria implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
 	 * 1. 条件属性
 	 */
	private String name;
	private Integer status;
	 /*
 	 * 2. 构造方法
 	 */
	
	public SysRoleCriteria(String name, Integer status) {
		super();
		this.name = name;
		this.status = status;
	}

	public SysRoleCriteria() {
		super();
	}

	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if(StringUtil.isNotNullAndEmpty(this.getName())){
			condition.append(" and name like #{name}");
			values.put("name", "%"+this.getName()+"%");
		}
		if(StringUtil.isNotNullAndEmpty(this.getStatus())){
			condition.append(" and status=#{status}");
			values.put("status", this.getStatus());
		}
		return condition.toString();
	}
	/*
 	 * 4. Setters & Getters...
 	 */ 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}