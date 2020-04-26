package com.ideepmind.mail;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModelMapper {
	
	/**
	* @方法名称: selectAllList
	* @描述: TODO(新增客户查询)
	* @事件： 2020年3月20日 下午6:55:15
	* @param map(todayDate传入时间为当天，否则为一周的数据)
	* 			(startDate,endDate为一周时间当前周一到周日)
	* 			(warAreaId传入为查询二网数据否则查询全部)
	* @return
	*/
	public List<Map<String,Object>> selectAllList(Map<String,Object> map);
	
	/**
	* @方法名称: selectAllShareList
	* @描述: TODO(新增分享潜客查询)
	* @事件： 2020年3月20日 下午6:55:15
	* @param map(todayDate传入时间为当天，否则为一周的数据)
	* 			(startDate,endDate为一周时间当前周一到周日)
	* 			(warAreaId传入为查询二网数据否则查询全部)
	* @return
	*/
	public List<Map<String,Object>> selectAllShareList(Map<String,Object> map);
	
}