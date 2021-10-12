package com.mycompany.comento.dao;

import java.util.HashMap;
import java.util.List;

import com.mycompany.comento.dto.StatisticDto;

public interface StatisticMapper {

	public HashMap<String, Object> selectYearLogin(String year);
	
//	월별 접속자 수
	public List<StatisticDto.MonthDto> selectMonthLogin(String year);
	
//	일별 접속자 수
//	중복을 제거한 로그인한 기록이 있는 해당 월을 나타낸다.
	public List<StatisticDto.DistinctMonthDto> selectDistinctMonth(String year);

	public List<StatisticDto.DayMonthDto> selectDayLogin(String year);
	
//	하루 평균 로그인 수
	public HashMap<String, Object> selectAvgDayLogin(String year);

//	휴일을 제외한 로그인 수
	public HashMap<String, Object> selectExceptHolidayLogin(HashMap<String, Object> map);
	
//	부서별 월별 로그인 수
	public List<HashMap<String, String>> selectDistinctDept();
	
	public List<StatisticDto.DeptMonthDto> selectDeptMonthLogin(String year);
	
}
