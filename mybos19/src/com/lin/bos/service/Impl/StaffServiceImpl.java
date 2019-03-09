package com.lin.bos.service.Impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.bos.dao.IStaffDao;
import com.lin.bos.dao.impl.StaffDaoImpl;
import com.lin.bos.domain.Staff;
import com.lin.bos.service.IStaffService;
import com.lin.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	@Autowired
	private StaffDaoImpl staffDao;

	@Override
	public void save(Staff model) {
		// TODO Auto-generated method stub
		staffDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pagebean) {
		// TODO Auto-generated method stub
		staffDao.pageQuery(pagebean);
	}

	@Override
	public Staff findById(String id) {
		// TODO Auto-generated method stub
		return staffDao.findById(id);
	}

	@Override
	public void update(Staff staff) {
		// TODO Auto-generated method stub
		staffDao.update(staff);
	}

	@Override
	public void deleteBatch(String ids) {
		// TODO Auto-generated method stub
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}

	@Override
	public List<Staff> findListNotDelete() {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.ne("deltag", "1"));
		return staffDao.findByCriteria(detachedCriteria);
	}
}
