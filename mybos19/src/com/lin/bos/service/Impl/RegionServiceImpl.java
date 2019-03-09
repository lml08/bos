package com.lin.bos.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.bos.dao.IRegionDao;
import com.lin.bos.dao.impl.RegionDaoImpl;
import com.lin.bos.domain.Region;
import com.lin.bos.service.IRegionService;
import com.lin.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private RegionDaoImpl regionDao;
	
	@Override
	public void saveBatch(List<Region> list) {
		// TODO Auto-generated method stub
		for(Region region : list) {
			regionDao.saveOrUpdate(region);
		}
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		regionDao.pageQuery(pageBean);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return regionDao.findAll();
	}

	@Override
	public List<Region> findByQ(String q) {
		// TODO Auto-generated method stub
		return regionDao.findByQ(q);
	}
	
}
