package com.ideepmind.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class Timer {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static String ID = "WA8f977c8c2cabjbccebbcgegceee9840f";
	private final static String toXlsx = "战区,大区,省份,新增客户ID,新增客户姓名,新增客户手机号,增加时间,销售顾问名称,销售顾问所在门店";
	private final static String toAllXlsx = "日期,战区,大区,省份,经销商简称,产品体验师,账号,联系方式,Jtools首次登录时间,二维码分享好友次数,二维码分享朋友圈次数,新增留资量";
	
	private final static String receiverList = "zhanghui@ideepwise.ai,chenyu@ideepwise.ai";
	private final static String copyReceiverList = "mazhitao@ideepwise.ai,liujun@ideepwise.ai";
	
	private final static String receiverList2 = "shakun@mychery.com";
	private final static String copyReceiverList2 = "zhanghui@ideepwise.ai";
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	* @方法名称: excelTimer
	* @描述: TODO(每天晚上8点运行该方法)
	* @事件： 2020年3月23日 下午4:00:05
	*/
	//@Scheduled(cron = "0 0 20 * * ?")
	public void excelTimer() {
		List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> fileListTwo = new ArrayList<Map<String,Object>>();
		String context = "您好，附件为今天新增客户信息和分享数据新增潜客信息，请查收。"; 
		String context2 = "您好，附件为今天二网新增客户信息和分享数据新增潜客信息，请查收。";
		
		String string = dateFormat.format(new Date());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("todayDate", string);
		
		List<Map<String,Object>> selectAllList = modelMapper.selectAllList(map);
		if(selectAllList != null && selectAllList.size() > 0) {
			String filePath1 = "mzt-新增客户数据-"+ string +".xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelXlsx(selectAllList, filePath1, toXlsx);
			fileList.add(excelXlsx);
		}else{
			context += "	今日无新增客户信息。";
		}
		
		List<Map<String,Object>> selectAllShareList = modelMapper.selectAllShareList(map);
		if(selectAllShareList != null && selectAllShareList.size() > 0) {
			String filePath2 = "获取登录数据分享数据新增潜客数据-"+ string +".xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelAllXlsx(selectAllShareList, filePath2, toAllXlsx);
			fileList.add(excelXlsx);
		}
		
		map.put("warAreaId", ID);
		List<Map<String,Object>> selectAllList2 = modelMapper.selectAllList(map);
		if(selectAllList2 != null && selectAllList2.size() > 0) {
			String filePath3 = "二网-mzt-新增客户数据-"+ string +".xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelXlsx(selectAllList2, filePath3, toXlsx);
			fileList.add(excelXlsx);
			fileListTwo.add(excelXlsx);
		}else {
			if(!context.contains("今日无新增客户信息")) {
				context += "	今日二网无新增客户信息。";
			}
			context2 += "	今日二网无新增客户信息。";
		}
		
		List<Map<String,Object>> selectAllShareList2 = modelMapper.selectAllShareList(map);
		if(selectAllShareList2 != null && selectAllShareList2.size() > 0) {
			String filePath4 = "二网-获取登录数据分享数据新增潜客数据-"+ string +".xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelAllXlsx(selectAllShareList2, filePath4, toAllXlsx);
			fileList.add(excelXlsx);
			fileListTwo.add(excelXlsx);
		}
		
		//发送邮件
		//主题
		String subject = "新增客户信息和分享数据新增潜客信息-"+string;
		if(fileList.size() == 0) {
			context = "您好，今日无新增数据!";
		}
		//发送邮件给张辉
		MailUtil.send(subject, context, receiverList, copyReceiverList, fileList);
		
		String subject2 = "二网新增客户信息和分享数据新增潜客信息-"+string;
		if(fileListTwo.size() == 0) {
			context2 = "您好，二网今日无新增数据!";
		}
		//发送邮件给shakun
		MailUtil.send(subject2, context2, receiverList2, copyReceiverList2, fileListTwo);
	}
	
	/**
	* @方法名称: sundayExcelTimer
	* @描述: TODO(每周日晚上8点10分运行该方法)
	* @事件： 2020年3月24日 上午11:48:54
	*/
	//@Scheduled(cron = "0 10 20 ? * SUN")
	public void sundayExcelTimer() {
		List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> fileListTwo = new ArrayList<Map<String,Object>>();
		String context = "您好，附件为本周新增客户信息和分享数据新增潜客信息，请查收。"; 
		String context2 = "您好，附件为本周二网新增客户信息和分享数据新增潜客信息，请查收。"; 
		
		String string = dateFormat.format(new Date());
		Map<String,Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		String format = dateFormat.format(calendar.getTime());
		map.put("startDate", format+" 00:00:00");
		map.put("endDate", string+" 23:59:59");
		
		List<Map<String,Object>> selectAllList = modelMapper.selectAllList(map);
		if(selectAllList != null && selectAllList.size() > 0) {
			String filePath1 = format + "至"+string+"mzt-新增客户数据.xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelXlsx(selectAllList, filePath1, toXlsx);
			fileList.add(excelXlsx);
		}else{
			context += "	本周无新增客户信息。";
		}
		
		List<Map<String,Object>> selectAllShareList = modelMapper.selectAllShareList(map);
		if(selectAllShareList != null && selectAllShareList.size() > 0) {
			String filePath2 = format + "至"+string+"获取登录数据分享数据新增潜客数据.xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelAllXlsx(selectAllShareList, filePath2, toAllXlsx);
			fileList.add(excelXlsx);
		}
		
		map.put("warAreaId", ID);
		List<Map<String,Object>> selectAllList2 = modelMapper.selectAllList(map);
		if(selectAllList2 != null && selectAllList2.size() > 0) {
			String filePath3 = format + "至"+string+"二网-mzt-新增客户数据.xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelXlsx(selectAllList2, filePath3, toXlsx);
			fileList.add(excelXlsx);
			fileListTwo.add(excelXlsx);
		}else {
			if(!context.contains("本周无新增客户信息")) {
				context += "	本周二网无新增客户信息。";
			}
			context2 += "	本周二网无新增客户信息。";
		}
		
		List<Map<String,Object>> selectAllShareList2 = modelMapper.selectAllShareList(map);
		if(selectAllShareList2 != null && selectAllShareList2.size() > 0) {
			String filePath4 = format + "至"+string+"二网-获取登录数据分享数据新增潜客数据.xlsx";
			Map<String, Object> excelXlsx = ExcelUtil.toExcelAllXlsx(selectAllShareList2, filePath4, toAllXlsx);
			fileList.add(excelXlsx);
			fileListTwo.add(excelXlsx);
		}
		
		//发送邮件
		//主题
		String subject = format + "至"+string+"新增客户信息和分享数据新增潜客信息";
		if(fileList.size() == 0) {
			context = "您好，本周无新增数据!";
		}
		//发送邮件给张辉
		MailUtil.send(subject, context, receiverList, copyReceiverList, fileList);
		
		String subject2 = format + "至"+string+"二网新增客户信息和分享数据新增潜客信息";
		if(fileListTwo.size() == 0) {
			context2 = "您好，二网本周无新增数据!";
		}
		//发送邮件给shakun
		MailUtil.send(subject2, context2, receiverList2, copyReceiverList2, fileListTwo);
	}

}
