package com.lin.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lin.bos.domain.Staff;
import com.lin.bos.service.IStaffService;
import com.lin.bos.utils.PageBean;
import com.lin.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String add() {
		staffService.save(model);
		return "list";
	}
	
	public String delete() {
		staffService.deleteBatch(ids);
		return "list";
	}
	
	public String pageQuery() throws IOException {
		
		staffService.pageQuery(pagebean);
		this.writePageBean2Json(pagebean,new String[] {"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String edit() {
		//这里不能直接对model对象进行update 因为还涉及了分区的外键 
		Staff staff = staffService.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staffService.update(staff);
		return "list";
	}
	public String listajax() throws IOException {
		List<Staff> list = staffService.findListNotDelete();
		String[] excludes = new String[] {"decidedzones"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
}
