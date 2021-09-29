package com.mycompany.comento.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.comento.service.StatisticService;

@Controller
public class SettingTest {
	
	@Autowired
	private StatisticService service;
	
	@ResponseBody
	@RequestMapping("/sqlyearStatistic")
	public Map<String, Object> sqltest(String year) throws Exception{
		HashMap<String, Object> result = service.yearloginNum(year);
		
		for(Entry<String, Object> entrySet : result.entrySet()) {
			System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
		}
		
		return service.yearloginNum(year);
	}
	
	@RequestMapping("/test")
	public ModelAndView test() throws Exception{
		
		ModelAndView mav = new ModelAndView("test");
		mav.addObject("name", "devfunpj");
		
		List<String> resultList = new ArrayList<String>();
		resultList.add("!!!HEELO WORLD!!!");
		resultList.add("설정 TEST!");
		resultList.add("설정 TEST!!");
		resultList.add("설정 TEST!!!");
		resultList.add("설정 TEST!!!!");
		mav.addObject("list", resultList);
		
		return mav;
		
	}

}
