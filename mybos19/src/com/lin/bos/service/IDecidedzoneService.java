package com.lin.bos.service;

import com.lin.bos.domain.Decidedzone;
import com.lin.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model,String[] subareaid);

	public void pageQuery(PageBean pagebean);

}
