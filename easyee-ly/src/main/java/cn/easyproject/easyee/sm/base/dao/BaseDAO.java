package cn.easyproject.easyee.sm.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.sm.base.pagination.PageBean;

/**
 * description: DAO基类 <br/>
 * date: 2017年9月25日 上午11:54:18 <br/>
 * author: gaojx <br/>
 * copyright: 北京志诚泰和信息技术有限公司
 */
public interface BaseDAO<T> {

    /**
     * Save
     * @param o Object
     */
    public int save(T o);

    /**
     * Delete Object
     * @param o Object
     */
    public int delete(T o);

    /**
     * Delete All
     * @param ids Object Primary Keys
     */
    public int deleteByIds(String[] ids);

    /**
     * Update
     * @param o Object
     * @return 
     */
    public int update(T o);

    /**
     * Update Batch
     * @param m Map
     * @return
     */
    public int updateBatch(Map<String, Object> m);
    
    /**
     * Get
     * @param id Object Primary Key
     * @return Object
     */
    public T get(Serializable id);

    /**
     * Pagination
     * @param pageBean PageBean
     */
    public List<T> pagination(PageBean<T> pageBean);

    /**
     * Find all
     */
    public List<T> findAll();
    
    /**
     * Find row count
     */
    public int findMaxRow();
    
    /**
     * Query list by custom condition
     * @param Map m
     */
    public List<T> query(Map<String, Object> m);
    
}
