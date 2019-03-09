package com.lin.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lin.bos.domain.Region;
import com.lin.bos.domain.Subarea;
import com.lin.bos.service.ISubareaService;
import com.lin.bos.utils.FileUtils;
import com.lin.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;
	
	public String add() {
		subareaService.save(model);		
		return "list"; 
	}
public String pageQuery() throws IOException {
		//有条件的查询 要先封装
		DetachedCriteria detachedCriteria2 = pagebean.getDetachedCriteria();
		String addressKey = model.getAddresskey();
		Region region = model.getRegion();
		if(StringUtils.isNotBlank(addressKey)) {
			detachedCriteria2.add(Restrictions.like("addressKey",addressKey));
		}
		if(region != null) {
			//创建别名，用于多表关联查询
			detachedCriteria2.createAlias("region", "r");
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			if(StringUtils.isNotBlank(province)) {
				detachedCriteria2.add(Restrictions.ilike("r.province", "%"+province +"%"));
			}
			if(StringUtils.isNotBlank(city)) {
				detachedCriteria2.add(Restrictions.ilike("r.city", "%"+city +"%"));
			}
			if(StringUtils.isNotBlank(district)) {
				detachedCriteria2.add(Restrictions.ilike("r.district", "%"+district +"%"));
			}
		}
		subareaService.pageQuery(pagebean);
		//将pagebean对象转为json
		this.writePageBean2Json(pagebean,new String[] {"detachedCriteria","currentPage","pageSize","decidedzone","subareas"});
		return NONE;
	}	
	

	public String exportXls() throws IOException {
		List<Subarea> list = subareaService.findAll();
		//创建一个sheet页
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个sheet页
		HSSFSheet sheet = workbook.createSheet( "分区数据");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		
		for(Subarea subarea:list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
		}
		//一流两(http)头
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//中文会出错 使用工具类编码
		filename = FileUtils.encodeDownloadFilename(filename,agent);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		//获取文件后缀所对应的文件类型
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	public String listajax() throws IOException {
		//查询没有关联到定区的分区
		List<Subarea> list = subareaService.findAll();
		String[] excludes = new String[] {"decidedzone","region"};
		this.writeList2Json(list, excludes);
		subareaService.findListNotAssociation();
		return NONE;
	}
}
