package com.lin.bos.service.Impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.bos.dao.impl.SubareaDaoImpl;
import com.lin.bos.domain.Subarea;
import com.lin.bos.service.ISubareaService;
import com.lin.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private SubareaDaoImpl subareaDao;
	@Override
	public void pageQuery(PageBean pagebean) {
		// TODO Auto-generated method stub
		subareaDao.pageQuery(pagebean);
	}
	@Override
	public void save(Subarea model) {
		// TODO Auto-generated method stub
		subareaDao.save(model);
	}
	@Override
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaDao.findAll();
	}
	@Override
	public List<Subarea> findListNotAssociation() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}
	
}
