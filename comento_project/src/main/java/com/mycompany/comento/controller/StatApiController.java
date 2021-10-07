package com.mycompany.comento.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

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
	public Result getMonthLogin(@PathVariable String year){
		
		// 총 합계
		int totCnt = service.selectYearTotLogin(year);
		
		// 월별 로그인 수
		List<StatisticDto> findStatic = service.selectMonthLogin(year);
		
		
		List<MonthDto> dto = new ArrayList<MonthDto>();
		for(StatisticDto array : findStatic) {
			dto.add(new MonthDto(array.getMonth(), array.getLoginNum()));
		}
		
		
		return new Result<>(totCnt,year, dto);
	}
	
	// 일별 로그인 수
	@GetMapping("/statisticLogin/day/{year}")
	public Result getDayLogin(@PathVariable String year) {
		
//		int totCnt = service.selectMonthTotLogin(year);
		
		List<StatisticDto> findMonth = service.selectDistinctMonth(year);
		
		List<DayMonthDto> dto = new ArrayList<>();
		for(StatisticDto array : findMonth) {
			dto.add(new DayMonthDto(array.getMonth(), find_day(array.getMonth())));
		}
		
		return new Result<>(100, year, dto);
		
	}
	
	private List<DayDto> find_day(String month) {
		List<StatisticDto> findDay = service.selectDayLoginByMonth(month);
		
		List<DayDto> dto = new ArrayList<>();
		for(StatisticDto array : findDay) {
			dto.add(new DayDto(array.getDay(), array.getLoginNum()));
		}
		
		return dto;
	}
	
	@GetMapping("/statisticLogin/avg/year/{year}")
	public Map<String, Object> getAvgLoginByYear(@PathVariable String year){
		return service.selectAvgDayLogin(year);
	}
	
	@GetMapping("/statisticLogin/test1")
	public void abc() throws Exception{
		
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=yFZtYw82i34Si9efOl96ZDk69kYsYnwOk9K%2FOu9OmgzPcEGt6y9DH7tqm%2Bki0TUpNMVyVR7%2FDlORn5gZh6%2Bp2Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode("2021", "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode("09", "UTF-8")); /*월*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        System.out.println("Response code: " + conn.getResponseCode());
        
        
        InputStream st =  conn.getInputStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(st);
        document.getDocumentElement().normalize();
        
        System.out.println("Root Element: " + document.getDocumentElement().getNodeName());
        
        
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(st));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        
	}

	

	@Data
	@AllArgsConstructor
	static class Result<T> {
		private int totCnt;
		private String year;
		private T data;
	}
	
	
	// 월별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	static class MonthDto{
		private String month;
		private int loginNum;
	}
	
	
	// ------- 일별 로그인 수 DTO --------
	@Data
	@AllArgsConstructor
	static class DayMonthDto{
		private String month;
		private List<DayDto> data;
	}
	
	@Data
	@AllArgsConstructor
	static class DayDto{
		private int day;
		private int loginNum;
	}
	
	
	
	
}
