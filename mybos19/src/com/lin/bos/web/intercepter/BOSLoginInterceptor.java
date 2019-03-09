package com.lin.bos.web.intercepter;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.lin.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		if(user==null) {
			return "login"; 
		}else
			return arg0.invoke();
	}
	
}
