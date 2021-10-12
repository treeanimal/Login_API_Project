package com.mycompany.comento.service;

import java.util.HashMap;
import java.util.List;

import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.dto.StatisticDto.Result;

public interface StatisticService {
	
	public HashMap<String, Object> yearloginNum (String year);
	
	// 월별 로그인 수
	public StatisticDto.Result getMonthLogin(String year);
	
	// 일별 로그인 수	
	public StatisticDto.Result getDayLogin(String year);
	
	// 평균 하루 로그인 수
	public HashMap<String, Object> getAvgDayLogin(String year);
	
	// 휴일을 제외한 로그인 수
	public HashMap<String, Object> getExceptHolidayLogin(String year) throws Exception;

	public Result getDeptMonthLogin(String year);
}
