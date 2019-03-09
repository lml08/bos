package com.lin.bos.service;

import java.util.List;

import com.lin.bos.domain.Subarea;
import com.lin.bos.utils.PageBean;

public interface ISubareaService {

	public void pageQuery(PageBean pagebean);

	public void save(Subarea model);

	public List<Subarea> findAll();

	public List<Subarea> findListNotAssociation();
	
}
