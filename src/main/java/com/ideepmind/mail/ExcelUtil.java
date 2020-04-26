package com.ideepmind.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtil {


	/**
	 * @方法名称: toExcelXlsx
	 * @描述: TODO(新增客户查询导出为excel)
	 * @事件： 2020年3月21日 下午2:38:24
	 * @param list 数据LIst
	 * @param filederPath 这里为导出文件存放的路径
	 * @param filePath 文件名称
	 * @param toXlsx 表头数据
	 */
	public static Map<String,Object> toExcelXlsx(List<Map<String,Object>> list,String filePath,String toXlsx) {
		String[] split = toXlsx.split(",");
		Map<String,Object> map1 = new HashMap<String,Object>();
		
		SXSSFWorkbook sxssfWorkbook = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ByteArrayOutputStream out = null;
		try {
			
			sxssfWorkbook = new SXSSFWorkbook(1000);
			Sheet sheet1 = sxssfWorkbook.createSheet("sheet1");
			
			int excelRow = 0;
			//标题行  
			Row titleRow = (Row) sheet1.createRow(excelRow++);  
		    for (int i = 0; i < split.length; i++) {  
		        Cell cell = titleRow.createCell(i);    
		        cell.setCellValue(split[i]);  
		    }
		    //内容
		    for (int i = 0; i < list.size(); i++) {
		    	Row contentRow = (Row) sheet1.createRow(excelRow++);
				Map<String, Object> map = list.get(i);
				
				Cell cell = contentRow.createCell(0);
				cell.setCellValue(map.get("c_war_area_name")==null?"":map.get("c_war_area_name").toString());
				
				Cell cell1 = contentRow.createCell(1);
				cell1.setCellValue(map.get("c_large_area_name")==null?"":map.get("c_large_area_name").toString());
				
				Cell cell2 = contentRow.createCell(2);
				cell2.setCellValue(map.get("c_area_name")==null?"":map.get("c_area_name").toString());
				
				Cell cell3 = contentRow.createCell(3);
				cell3.setCellValue(map.get("c_customer_id")==null?"":map.get("c_customer_id").toString());
				
				Cell cell4 = contentRow.createCell(4);
				cell4.setCellValue(map.get("c_customer_name")==null?"":map.get("c_customer_name").toString());
				
				Cell cell5 = contentRow.createCell(5);
				cell5.setCellValue(map.get("c_customer_moblie_phone_number")==null?"":map.get("c_customer_moblie_phone_number").toString());
				
				Cell cell6 = contentRow.createCell(6);
				cell6.setCellValue(map.get("c_create_time")==null?"":map.get("c_create_time").toString());
				
				Cell cell7 = contentRow.createCell(7);
				cell7.setCellValue(map.get("c_real_name")==null?"":map.get("c_real_name").toString());
				
				Cell cell8 = contentRow.createCell(8);
				cell8.setCellValue(map.get("c_store_name")==null?"":map.get("c_store_name").toString());
				
			}
		    for (int i = 0; i < split.length; i++) {  
		    	sheet1.autoSizeColumn(i);
	            sheet1.setColumnWidth(i,sheet1.getColumnWidth(i)*16/10);  
		    }
		    sheet1.setColumnWidth(3,sheet1.getColumnWidth(3)*10/16);
		    sheet1.setColumnWidth(5,sheet1.getColumnWidth(5)*10/15);
		    sheet1.setColumnWidth(6,sheet1.getColumnWidth(6)*10/15);
		    
		    out = new ByteArrayOutputStream();
		    
		    sxssfWorkbook.write(out);
		    byte[] byteArray = out.toByteArray();
		    byteArrayInputStream = new ByteArrayInputStream(byteArray);
		    DataSource dataSource = new ByteArrayDataSource(byteArrayInputStream,"application/excel");
		    map1.put("fileName", filePath);
		    map1.put("file", dataSource);
		    sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(byteArrayInputStream!=null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map1;
	}
	
	/**
	 * @方法名称: toExcelAllXlsx
	 * @描述: TODO(新增分享潜客查询导出为excel)
	 * @事件： 2020年3月21日 下午2:38:24
	 * @param list 数据LIst
	 * @param filederPath 这里为导出文件存放的路径
	 * @param filePath 文件名称
	 * @param toXlsx 表头数据
	 */
	public static Map<String,Object> toExcelAllXlsx(List<Map<String,Object>> list,String filePath,String toXlsx) {
		String[] split = toXlsx.split(",");
		Map<String,Object> map1 = new HashMap<String,Object>();
		
		SXSSFWorkbook sxssfWorkbook = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ByteArrayOutputStream out = null;
		try {
			sxssfWorkbook = new SXSSFWorkbook(1000);
			Sheet sheet1 = sxssfWorkbook.createSheet("sheet1");
			
			int excelRow = 0;
			//标题行  
			Row titleRow = (Row) sheet1.createRow(excelRow++);  
			for (int i = 0; i < split.length; i++) {  
				Cell cell = titleRow.createCell(i);    
				cell.setCellValue(split[i]);  
			}
			//内容
			for (int i = 0; i < list.size(); i++) {
				Row contentRow = (Row) sheet1.createRow(excelRow++);
				Map<String, Object> map = list.get(i);
				
				Cell cell = contentRow.createCell(0);
				cell.setCellValue(map.get("nowDate")==null?"":map.get("nowDate").toString());
				
				Cell cell1 = contentRow.createCell(1);
				cell1.setCellValue(map.get("c_war_area_name")==null?"":map.get("c_war_area_name").toString());
				
				Cell cell2 = contentRow.createCell(2);
				cell2.setCellValue(map.get("c_large_area_name")==null?"":map.get("c_large_area_name").toString());
				
				Cell cell3 = contentRow.createCell(3);
				cell3.setCellValue(map.get("c_area_name")==null?"":map.get("c_area_name").toString());
				
				Cell cell4 = contentRow.createCell(4);
				cell4.setCellValue(map.get("c_store_name")==null?"":map.get("c_store_name").toString());
				
				Cell cell5 = contentRow.createCell(5);
				cell5.setCellValue(map.get("c_real_name")==null?"":map.get("c_real_name").toString());
				
				Cell cell6 = contentRow.createCell(6);
				cell6.setCellValue(map.get("c_user_name")==null?"":map.get("c_user_name").toString());
				
				Cell cell7 = contentRow.createCell(7);
				cell7.setCellValue(map.get("c_telephone")==null?"":map.get("c_telephone").toString());
				
				Cell cell8 = contentRow.createCell(8);
				cell8.setCellValue(map.get("c_sign_in_time")==null?"":map.get("c_sign_in_time").toString());
				
				CellStyle cellStyle = sxssfWorkbook.createCellStyle();
			    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
			    
				Cell cell9 = contentRow.createCell(9);
			    cell9.setCellStyle(cellStyle);
				cell9.setCellValue((long)map.get("shareOne"));
				
				Cell cell10 = contentRow.createCell(10);
				cell10.setCellStyle(cellStyle);
				cell10.setCellValue((long)map.get("shareTwo"));
				
				Cell cell11 = contentRow.createCell(11);
				cell11.setCellStyle(cellStyle);
				cell11.setCellValue((long)map.get("nescusNum"));
				
			}
			for (int i = 0; i < split.length; i++) {  
				sheet1.autoSizeColumn(i);
				sheet1.setColumnWidth(i,sheet1.getColumnWidth(i)*17/10);  
			}
			sheet1.setColumnWidth(0,sheet1.getColumnWidth(0)*10/17);
		    sheet1.setColumnWidth(5,sheet1.getColumnWidth(5)*10/16);
		    sheet1.setColumnWidth(6,sheet1.getColumnWidth(6)*10/16);
		    sheet1.setColumnWidth(7,sheet1.getColumnWidth(7)*10/16);
		    sheet1.setColumnWidth(8,sheet1.getColumnWidth(8)*10/16);
		    sheet1.setColumnWidth(9,sheet1.getColumnWidth(9)*5);
		    sheet1.setColumnWidth(10,sheet1.getColumnWidth(10)*6);
		    sheet1.setColumnWidth(11,sheet1.getColumnWidth(11)*4);
			
		    out = new ByteArrayOutputStream();
		    
		    sxssfWorkbook.write(out);
		    byte[] byteArray = out.toByteArray();
		    byteArrayInputStream = new ByteArrayInputStream(byteArray);
		    DataSource dataSource = new ByteArrayDataSource(byteArrayInputStream,"application/excel");
		    map1.put("fileName", filePath);
		    map1.put("file", dataSource);
		    sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(byteArrayInputStream!=null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map1;
	}
}
