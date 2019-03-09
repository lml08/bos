package com.lin.bos.web.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lin.bos.domain.Region;
import com.lin.bos.service.IRegionService;
import com.lin.bos.utils.PageBean;
import com.lin.bos.utils.PinYin4jUtils;
import com.lin.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	
	@Autowired
	private IRegionService regionService;
	private File myFile;
	
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
	public String importXls() throws IOException{
		String flag = "1";
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myFile));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<Region>();
			for(Row row : sheet) {
				int rowNum =row.getRowNum();
				if(rowNum == 0) {
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				
				city = city.substring(0,city.length()-1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(stringToPinyin,"");
				
				province = province.substring(0,province.length()-1);
				district = district.substring(0,district.length()-1);
				String info = province+city+district;
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(headByString,"");
				
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				list.add(region);
			}
			regionService.saveBatch(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag="0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	//分页查询
	public String pageQuery() throws IOException {
		
		regionService.pageQuery(pagebean);
		//将pagebean对象转为json
		this.writePageBean2Json(pagebean,new String[] {"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	public String listajax() throws IOException {
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findByQ(q);
		}else {
			list = regionService.findAll();
		}
		regionService.pageQuery(pagebean);
		this.writeList2Json(list,new String[]{"subareas"});
		return NONE;
	}
	//模糊查询条件
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
	
}
