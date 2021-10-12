package com.mycompany.comento.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.service.StatisticService;

@RestController
public class StatApiController {

	@Autowired
	private StatisticService service;

	@RequestMapping("/sqlyearStatistic")
	public Map<String, Object> sqltest(String year) throws Exception {

		return service.yearloginNum(year);
	}

	// 월별 로그인 수
	@GetMapping("/statistic/login/month/{year}")
	public StatisticDto.Result getMonthLogin(@PathVariable String year) {

		return service.getMonthLogin(year);
	}

	// 일별 로그인 수
	@GetMapping("/statistic/login/day/{year}")
	public StatisticDto.Result getDayLogin(@PathVariable String year) {

		return service.getDayLogin(year);

	}

	@GetMapping("/statistic/login/avg/{year}")
	public Map<String, Object> getAvgLoginByYear(@PathVariable String year) {
		return service.getAvgDayLogin(year);
	}

	@GetMapping("/statistic/login/exceptHOL/{year}")
	public HashMap<String, Object> getExceptHolidayLogin(@PathVariable String year) throws Exception {

		return service.getExceptHolidayLogin(year);
	}
	
	@GetMapping("/statistic/login/dept/month/{year}")
	public StatisticDto.Result getDeptMonthLogin(@PathVariable String year){
		
		return service.getDeptMonthLogin(year);
	}

	
}
