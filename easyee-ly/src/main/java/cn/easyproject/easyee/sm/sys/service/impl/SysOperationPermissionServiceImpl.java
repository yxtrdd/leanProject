package cn.easyproject.easyee.sm.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.base.util.StringUtil;
import cn.easyproject.easyee.sm.sys.dao.SysOperationPermissionDAO;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.sm.sys.service.SysOperationPermissionService;

@Service("sysOperationPermissionService")
public class SysOperationPermissionServiceImpl extends BaseServiceImpl<SysOperationPermission> implements SysOperationPermissionService {

    @Resource
    private SysOperationPermissionDAO sysOperationPermissionDAO;

    @Override
    public BaseDAO<SysOperationPermission> getDao() {
        return sysOperationPermissionDAO;
    }

    @Override
    public void findByPage(PageBean<SysOperationPermission> pageBean, EasyCriteria easyCriteria) {

    }

    @Override
    public void delete(String[] ids) {
        sysOperationPermissionDAO.deleteOperationRolesByIds(ids);
        sysOperationPermissionDAO.deleteByIds(ids);
    }

    @Override
    public void delete(SysOperationPermission sysOperationPermission) {
        sysOperationPermissionDAO.deleteOperationRoles(sysOperationPermission);
        sysOperationPermissionDAO.delete(sysOperationPermission);
    }

    @Override
    public List<Map<?, ?>> list(Integer menuId) {
        return sysOperationPermissionDAO.list(menuId);
    }
    @Override
    public List<String> getIdsByRoleId(Integer roleId) {
        return sysOperationPermissionDAO.getIdsByRoleId(roleId);
    }

    @Override
    public void deleteByMenuPermissionId(int menuPermissionId) {
        sysOperationPermissionDAO.deleteByMenuPermissionId(menuPermissionId);
    }

    @Override
    public Map<String, String> getAllOpreationNames() {
        Map<String, String> operationsName = new HashMap<String, String>();
        List<Map<String, String>> list = sysOperationPermissionDAO.getAllOpreationNames();

        String operationName = null; // 权限动作对应的权限提示名称
        for (Map<String, String> map : list) {
            if (StringUtil.isNotNullAndEmpty(map.get("action"))) {
                for (String o : map.get("action").split("#|,")) {
                    o = o.trim();
                    if (o.length() > 0) {
                        String name = map.get("name");
                        String remark = map.get("remark");
                        operationName = name;
                        // 如果存在备注
                        if (StringUtil.isNotNullAndEmpty(remark)) {
                            operationName += "（" + remark + "）";
                        }
                        operationsName.put(o, operationName);
                    }
                }
            }
        }
        return operationsName;
    }
}
