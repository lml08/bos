package com.lin.bos.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.bos.dao.IDecidedzoneDao;
import com.lin.bos.dao.ISubareaDao;
import com.lin.bos.domain.Decidedzone;
import com.lin.bos.domain.Subarea;
import com.lin.bos.service.IDecidedzoneService;
import com.lin.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	@Override
	public void save(Decidedzone model,String[] subareaid) {
		// TODO Auto-generated method stub
		decidedzoneDao.save(model);
		for(String sid : subareaid) {
			Subarea subarea = subareaDao.findById(sid);//持久对象
			//分区对象关联定区对象---多方关联一方
			subarea.setDecidedzone(model);
		}
		
	}
	@Override
	public void pageQuery(PageBean pagebean) {
		// TODO Auto-generated method stub
		decidedzoneDao.pageQuery(pagebean);
	}
}
