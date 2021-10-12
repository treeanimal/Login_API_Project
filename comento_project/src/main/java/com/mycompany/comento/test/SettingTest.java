package com.mycompany.comento.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SettingTest {

	@RequestMapping("/test")
	public ModelAndView test() throws Exception {

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
