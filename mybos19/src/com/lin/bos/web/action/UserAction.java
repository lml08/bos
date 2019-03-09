package com.lin.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lin.bos.crm.CustomerService;
import com.lin.bos.domain.User;
import com.lin.bos.service.IUserService;
import com.lin.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	private String checkcode;
	@Resource
	private IUserService userService;
	

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}


	public String login() {
	
		
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			
			User user =  userService.login(model);
			if(user!=null) {
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else {
				this.addActionError(this.getText("nameorpasserror"));
				return "login";
				
			}
		}else {
			this.addActionError("checknodeerror");
			return "login";
		}
	}
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	public String editPassword() throws IOException{
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		System.out.println("1");
		String password = model.getPassword();//������
		System.out.println(password);
		String flag="1";
		try {
			userService.editPassword(password,user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag="0";//�޸�ʧ��
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
}
