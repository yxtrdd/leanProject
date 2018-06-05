package cn.easyproject.easyee.sm.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;

/**
 * description: 业务实现基类 <br/>
 * date: 2017年9月25日 下午2:34:26 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 * 
 * @param <T>
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public abstract BaseDAO<T> getDao();

    @Override
    public void save(T o) {
        getDao().save(o);
    }

    @Override
    public void delete(T o) {
        getDao().delete(o);
    }

    @Override
    public void delete(String[] ids) {
        getDao().deleteByIds(ids);
    }

    @Override
    public void update(T o) {
        getDao().update(o);
    }

    @Override
    @Transactional(readOnly = true)
    public T get(Serializable id) {
        return getDao().get(id);
    }

    @Override
    @Transactional(readOnly = true)
    public abstract void findByPage(PageBean<T> pageBean, EasyCriteria easyCriteria);

    @Override
    @Transactional(readOnly = true)
    public int findMaxRow() {
        return getDao().findMaxRow();
    }

    @Override
    @Transactional(readOnly = true)
    public int findMaxPage(int rowPerPage) {
        return (getDao().findMaxRow() - 1) / rowPerPage + 1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> query(Map<String, Object> m) {
        return getDao().query(m);
    }

}
