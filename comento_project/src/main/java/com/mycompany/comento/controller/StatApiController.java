package com.mycompany.comento.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.comento.controller.StatApiController.MonthDto;
import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.service.StatisticService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class StatApiController {

	@Autowired
	private StatisticService service;
	
	@RequestMapping("/sqlyearStatistic")
	public Map<String, Object> sqltest(String year) throws Exception{
		HashMap<String, Object> result = service.yearloginNum(year);
		
		for(Entry<String, Object> entrySet : result.entrySet()) {
			System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
		}
		
		return service.yearloginNum(year);
	}
	
	// 월별 로그인 수
	@GetMapping("/statisticLogin/month/{year}")
	public Result<List<MonthDto>> getMonthLogin(@PathVariable String year){
		
		// 총 합계
		int totCnt = service.selectYearTotLogin(year);
		
		// 월별 로그인 수
		List<StatisticDto> findStatic = service.selectMonthLogin(year);
		
		
		List<MonthDto> dto = new ArrayList<MonthDto>();
		for(StatisticDto array : findStatic) {
			dto.add(new MonthDto(array.getMonth(), array.getLoginNum()));
		}
		
		
		return new Result<>(totCnt, dto);
	}
	
	@GetMapping("/test/{year}")
	public String test(@PathVariable String year) {
		service.selectDistinctMonth(year);
		return "good";
	}
	
	// 일별 로그인수
	@GetMapping("/statisticLogin/day/{yearMonth}")
	public Result<List<DayDto>> getDayLogin(@PathVariable String yearMonth){
		
		// 총 합계
		int totCnt = service.selectMonthTotLogin(yearMonth);
		
		
		List<StatisticDto> findDto =  service.selectDayLogin(yearMonth);
		
		List<DayDto> dto = new ArrayList<DayDto>();
		for(StatisticDto array : findDto) {
			dto.add(new DayDto(array.getDay(), array.getLoginNum()));
		}
		
		
		return new Result<>(totCnt, dto);
	}
	

	@Data
	@AllArgsConstructor
	static class Result<T> {
		private int totCnt;
		private T data;
	}
	
	
	// 월별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	static class MonthDto{
		private String month;
		private int loginNum;
	}
	
	
	// 일별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	static class DayDto{
		private String day;
		private int loginNum;
	}
	
	
}
