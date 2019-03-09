package com.lin.bos.service;

import java.util.List;

import com.lin.bos.domain.Region;
import com.lin.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List findAll();

	public List<Region> findByQ(String q);

}
