package com.lin.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lin.bos.domain.Decidedzone;
import com.lin.bos.domain.Region;
import com.lin.bos.utils.PageBean;

/**
 *
 * 
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(Region region);
	public T findById(Serializable id);
	public List<T> findAll();
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
	public void executeUpdate(String queryName,Object ...objects);
	public void pageQuery(PageBean pagebean);
	
}
