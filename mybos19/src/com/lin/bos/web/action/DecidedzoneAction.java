package com.lin.bos.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lin.bos.domain.Decidedzone;
import com.lin.bos.service.IDecidedzoneService;
import com.lin.bos.utils.PageBean;
import com.lin.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	@Autowired
	private IDecidedzoneService decidedzoneService;
	private String[] subareaid;
	
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	public String add() throws IOException {
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	
	public String pageQuery() throws IOException {
		
		decidedzoneService.pageQuery(pagebean);
		this.writePageBean2Json(pagebean,new String[] {"decidedzones","subareas","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	public String findnoassociationCustomers() throws IOException {
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[] {"station","address"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	public String findhasassociationCustomers() throws IOException {
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		String[] excludes = new String[] {"station","address"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	private Integer[] customerIds;
	
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	public String assigncustomerstodecidedzone() {
	
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
}
