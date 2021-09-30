package com.mycompany.comento.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.comento.dao.StatisticMapper;
import com.mycompany.comento.dto.StatisticDto;

@Service
public class StatisticServiceImpl implements StatisticService{

	@Autowired
	private StatisticMapper uMapper;
	
	@Override
	public HashMap<String, Object> yearloginNum(String year) {
		
		HashMap<String, Object> retVal = new HashMap<String, Object>();
		
		try {
			retVal = uMapper.selectYearLogin(year);
			retVal.put("year", year);
			retVal.put("is_success", true);
		}catch(Exception e) {
			retVal.put("totCnt", -999);
			retVal.put("year", year);
			retVal.put("is_success", false);
		}
		
		return retVal;
	}

	@Override
	public List<StatisticDto> selectMonthLogin(String year) {
		
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

}
