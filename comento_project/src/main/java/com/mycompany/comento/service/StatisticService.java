package com.mycompany.comento.service;

import java.util.HashMap;
import java.util.List;

import com.mycompany.comento.dto.StatisticDto;

public interface StatisticService {
	
	public HashMap<String, Object> yearloginNum (String year);
	
	public List<StatisticDto> selectMonthLogin(String year);
//	해당 연도 총 접속자 수
	public int selectYearTotLogin(String year);
//	해당 월 총 접속자 수
	public int selectMonthTotLogin(String yearMonth);
	
	public List<StatisticDto> selectDayLogin(String yearMonth);
}
