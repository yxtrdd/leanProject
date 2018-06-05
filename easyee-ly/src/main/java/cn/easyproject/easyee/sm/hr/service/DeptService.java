package cn.easyproject.easyee.sm.hr.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.hr.entity.Dept;

/**
 * 业务接口统一继承 BaseService基类
 * @author easyproject.cn
 * @version 1.0
 */
@Transactional
public interface DeptService extends BaseService<Dept> {

}
