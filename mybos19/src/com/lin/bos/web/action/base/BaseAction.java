package com.lin.bos.web.action.base;



import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.lin.bos.crm.CustomerService;
import com.lin.bos.domain.Region;
import com.lin.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	//ģ�Ͷ���
	@Resource
	protected CustomerService customerService;
	protected T model;
	protected PageBean pagebean = new PageBean();
	protected DetachedCriteria detachedCriteria=null;
	public void setPage(int page) {
		pagebean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pagebean.setPageSize(rows);
	}
	//然后需要把pagebean对象转化成datagrid显示需要的格式   实体类转json
	public void writePageBean2Json(PageBean pagebean,String[] excludes) throws IOException{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pagebean,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	public void writeList2Json(List list, String[] excludes) throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	//�ڹ��췽���ж�̬���ʵ�����ͣ�ͨ�����䴴��ģ�Ͷ���
	public BaseAction() {
		//��������
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pagebean.setDetachedCriteria(detachedCriteria);
		
		try {
			//反射
			model = entityClass.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
