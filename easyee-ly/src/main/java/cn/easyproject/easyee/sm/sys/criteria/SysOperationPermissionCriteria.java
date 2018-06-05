package cn.easyproject.easyee.sm.sys.criteria;

import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;

/**
 * description: TODO(功能描述) <br/>
 * date: 2017年9月29日 下午4:23:24 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 */
public class SysOperationPermissionCriteria extends EasyCriteria {

    private String menuId;

    @Override
    public String getCondition() {
        return null;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
