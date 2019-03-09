package com.lin.bos.service;

import java.util.List;

import com.lin.bos.domain.Staff;
import com.lin.bos.utils.PageBean;

public interface IStaffService {
	void save(Staff model);

	public void pageQuery(PageBean pagebean);

	Staff findById(String id);

	void update(Staff staff);

	void deleteBatch(String ids);

	public List<Staff> findListNotDelete();
		
}
