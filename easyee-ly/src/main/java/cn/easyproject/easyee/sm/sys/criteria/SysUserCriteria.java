package cn.easyproject.easyee.sm.sys.criteria;

import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.util.StringUtil;

/**
 * SysUser查询标准条件类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /*
     * 条件属性
     */
    private String name;
    private String realName;
    private Integer status; // 用户状态：0启用；1禁用；2删除

    private String dwbm;

    /*
     * 2. 构造方法
     */
    public SysUserCriteria() {
    }

    public SysUserCriteria(String name, String realName, Integer status) {
        super();
        this.name = name;
        this.realName = realName;
        this.status = status;
    }

    /*
     * 3. 条件生成抽象方法实现
     */
    public String getCondition() {
        values.clear(); // 清除条件数据
        StringBuffer condition = new StringBuffer();
        
        if (StringUtil.isNotNullAndEmpty(this.getName())) {
            condition.append(" and name like #{name}");
            values.put("name", "%" + this.getName() + "%");
        }
        if (StringUtil.isNotNullAndEmpty(this.getRealName())) {
            condition.append(" and real_Name like #{realName}");
            values.put("realName", "%" + this.getRealName() + "%");
        }
        if (StringUtil.isNotNullAndEmpty(this.getStatus())) {
            condition.append(" and status=#{status}");
            values.put("status", this.getStatus());
        }
        if (StringUtil.isNotNullAndEmpty(this.getDwbm())) {
            if(this.getDwbm().length() == 5){
                // 电厂用户
            } else {
                condition.append(" and (dwbm like #{dwbm} or length(dwbm) = 5)");
                values.put("dwbm", this.getDwbm() + "%");
            }
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDwbm() {
        return dwbm;
    }

    public void setDwbm(String dwbm) {
        this.dwbm = dwbm;
    }

}
