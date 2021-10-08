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
	
	public List<StatisticDto.MonthDto> selectMonthLogin(String year);
	
	public List<StatisticDto> selectDayLogin(String yearMonth);
	
//	----------- 일별 접속자 수
//	중복을 제거한 로그인한 기록이 있는 해당 월을 나타낸다.
	public List<StatisticDto.MonthDto> selectDistinctMonth(String year);
	
//	해당 월의 하루 방문자 수
	public List<StatisticDto.DayDto> selectDayLoginByMonth(int month);
	
//	---------- 하루 평균 로그인 수
	public HashMap<String, Object> selectAvgDayLogin(String year);
	
//	----------------- new ----------------------------------
}
