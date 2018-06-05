package cn.easyproject.easyee.sm.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.entity.SysLog;

@Transactional
public interface SysLogService extends BaseService<SysLog> {

}
