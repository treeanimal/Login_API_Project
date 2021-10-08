package com.mycompany.comento.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.comento.dao.StatisticMapper;
import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.dto.StatisticDto.Result;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticMapper uMapper;

	@Override
	public HashMap<String, Object> yearloginNum(String year) {

		HashMap<String, Object> retVal = new HashMap<String, Object>();

		try {
			retVal = uMapper.selectYearLogin(year);
			retVal.put("year", year);
			retVal.put("is_success", true);
		} catch (Exception e) {
			retVal.put("totCnt", -999);
			retVal.put("year", year);
			retVal.put("is_success", false);
		}

		return retVal;
	}

	@Override
	public List<StatisticDto.MonthDto> selectMonthLogin(String year) {

		return uMapper.selectMonthLogin(year);
	}

	@Override
	public int selectYearTotLogin(String year) {

		return uMapper.selectYearTotLogin(year);
	}

	@Override
	public List<StatisticDto> selectDayLogin(String yearMonth) {
		List<StatisticDto> findDto = uMapper.selectDayLogin(yearMonth);

		return findDto;
	}

	@Override
	public int selectMonthTotLogin(String yearMonth) {
		return uMapper.selectMonthTotLogin(yearMonth);
	}

//	하루 평균 로그인 수
	@Override
	public HashMap<String, Object> selectAvgDayLogin(String year) {
		HashMap<String, Object> map = new HashMap<>();

		map = uMapper.selectAvgDayLogin(year);
		return map;
	}

//	--------------- new ------------------------

	// 월별 로그인 수
	@Override
	public Result getMonthLogin(String year) {

		// 총 합계
		int totCnt = uMapper.selectYearTotLogin(year);
		System.out.println(totCnt);
		
		year = year.substring(2, year.length());

		// 월별 로그인 수
		List<StatisticDto.MonthDto> findStatic = uMapper.selectMonthLogin(year);

		List<StatisticDto.MonthDto> dto = new ArrayList<>();
		for (StatisticDto.MonthDto array : findStatic) {
			dto.add(new StatisticDto.MonthDto(array.getMonth(), array.getLoginNum()));
			totCnt += array.getLoginNum();
		}

		return new StatisticDto.Result<>(totCnt, Integer.parseInt(year), dto);
	}

	// 일별 로그인 수
	@Override
	public Result getDayLogin(String year) {

		// 
		List<StatisticDto.MonthDto> findMonth = uMapper.selectDistinctMonth(year);

		List<StatisticDto.DayMonthDto> dto = new ArrayList<>();
		for (StatisticDto.MonthDto array : findMonth) {
			dto.add(new StatisticDto.DayMonthDto(array.getMonth(), find_day(array.getMonth())));
		}

		return new Result<>(0, Integer.parseInt(year), dto);

	}
	
	private List<StatisticDto.DayDto> find_day(int month) {
		List<StatisticDto.DayDto> findDay = uMapper.selectDayLoginByMonth(month);

		List<StatisticDto.DayDto> dto = new ArrayList<>();
		for (StatisticDto.DayDto array : findDay) {
			dto.add(new StatisticDto.DayDto(array.getDay(), array.getLoginNum()));
		}

		return dto;
	}

}
