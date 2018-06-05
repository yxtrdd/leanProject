package cn.easyproject.easyee.sm.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.sm.base.dao.BaseDAO;
import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.pagination.PageBean;

/**
 * All Service extends BaseService
 * 业务接口基类
 * @author easyproject.cn
 * @version 1.0
 */
public interface BaseService<T> {

    /**
     * Get dao
     * @return
     */
    public BaseDAO<T> getDao();
    
    /**
     * Save
     * @param o Object
     */
    public void save(T o);
    
    /**
     * Delete Object
     * @param o Object
     */
    public void delete(T o);
    
    /**
     * Delete All
     * @param ids Object Primary Keys
     */
    public void delete(String[] ids);
    
    /**
     * Update
     * @param o Object
     */
    public void update(T o);
    
    /**
     * Get
     * @param id Object Primary Keys
     * @return Object
     */
    public T get(Serializable id);
    
    /**
     * Pagination
     * 
     * @param pageBean PageBean
     * @param easyCriteria EasyCriteria
     */
    public void findByPage(PageBean<T> pageBean, EasyCriteria easyCriteria);
    
    /**
     * Find all
     */
    public List<T> findAll();
    
    /**
     * Find row count
     */
    public int findMaxRow();
    
    /**
     * Max Page
     * @param rowPerPage Row Per Page
     * @return maxPage
     */
    public int findMaxPage(int rowsPerPage);
    
    /**
     * Query list by custom condition
     * @param Map o
     */
    public List<T> query(Map<String, Object> m);
    
}
