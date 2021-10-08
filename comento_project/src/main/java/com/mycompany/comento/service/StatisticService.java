package com.mycompany.comento.service;

import java.util.HashMap;
import java.util.List;

import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.dto.StatisticDto.Result;

public interface StatisticService {
	
	public HashMap<String, Object> yearloginNum (String year);
	
	public List<StatisticDto.MonthDto> selectMonthLogin(String year);
//	해당 연도 총 접속자 수
	public int selectYearTotLogin(String year);
//	해당 월 총 접속자 수
	public int selectMonthTotLogin(String yearMonth);
	
	public List<StatisticDto> selectDayLogin(String yearMonth);
	
//	---------- 일별 접속자 수 ---------------
//	public List<StatisticDto> selectDistinctMonth(String year);
	
//	public List<StatisticDto> selectDayLoginByMonth(String month);

//	---------- 하루 평균 로그인 수
	public HashMap<String, Object> selectAvgDayLogin(String year);
	
//	--------------------- new -----------------------------------
	
	// 월별 로그인 수
	public Result getMonthLogin(String year);
	
	// 일별 로그인 수
	public Result getDayLogin(String year);
	
}
