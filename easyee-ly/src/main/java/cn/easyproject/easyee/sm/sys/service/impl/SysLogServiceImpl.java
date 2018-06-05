package cn.easyproject.easyee.sm.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.impl.BaseServiceImpl;
import cn.easyproject.easyee.sm.sys.dao.SysLogDAO;
import cn.easyproject.easyee.sm.sys.entity.SysLog;
import cn.easyproject.easyee.sm.sys.service.SysLogService;

@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLog>  implements SysLogService {
    
	@Resource
	private SysLogDAO sysLogDAO;
	
    @Override
    public BaseDAO<SysLog> getDao() {
        return sysLogDAO;
    }

    @Override
	public void findByPage(PageBean<SysLog> pageBean, EasyCriteria sysLogCriteria) {
		pageBean.setSelect("*");
		pageBean.setFrom("sys_log s");
		pageBean.setEasyCriteria(sysLogCriteria);
		// 按条件分页查询
		sysLogDAO.pagination(pageBean);
	}

}
