package com.mycompany.comento.dao;

import java.util.HashMap;
import java.util.List;

import com.mycompany.comento.dto.StatisticDto;

public interface StatisticMapper {

	public HashMap<String, Object> selectYearLogin(String year);

//	해당 연도 총 접속자 수
	public int selectYearTotLogin(String year);
	
//	해당 월 총 접속자 수
	public int selectMonthTotLogin(String yearMonth);
	
	public List<StatisticDto> selectMonthLogin(String year);
	
	public List<StatisticDto> selectDayLogin(String yearMonth);
	

}
