package com.lin.bos.dao.base.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lin.bos.domain.Region;
import com.lin.bos.domain.Staff;
import com.lin.bos.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements com.lin.bos.dao.base.IBaseDao<T> {
	
	private Class<T> entityClass;
	
	@Resource
	// @Autowired
	// @Qualifier(value="abc")
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
		
	
	public BaseDaoImpl() {
		
		ParameterizedType genericSuperclass = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
		
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {// FROM User
		String hql = "FROM  " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 *  通用更新方法
	 */
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSession();// 从本地线程中获得session对象
		// 使用命名查询语句获得一个查询对象
		Query query = session.getNamedQuery(queryName);
		// 为hql语句中的？赋值
		int i = 0;
		for (Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();//执行更新
	}
	public void pageQuery(PageBean pagebean) {
		// TODO Auto-generated method stub
		int currentPage = pagebean.getCurrentPage();
		int pageSize = pagebean.getPageSize();
		DetachedCriteria detachedCriteria=pagebean.getDetachedCriteria();
		detachedCriteria.setProjection(Projections.rowCount());//select count(*) from bc_staff
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = list.get(0);
		pagebean.setTotal(total.intValue());
		detachedCriteria.setProjection(null);
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage-1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pagebean.setRows(rows);
	}


	@Override
	public void saveOrUpdate(Region region) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(region);
	}
	
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
	}
}
