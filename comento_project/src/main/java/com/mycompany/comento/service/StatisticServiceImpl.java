package com.mycompany.comento.service;

import java.io.BufferedReader;
import java.io.IOException;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mycompany.comento.dao.StatisticMapper;
import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.dto.StatisticDto.DeptDto;
import com.mycompany.comento.dto.StatisticDto.DeptMonthDto;
import com.mycompany.comento.dto.StatisticDto.MonthDto;
import com.mycompany.comento.dto.StatisticDto.Result;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Value("${openapi.url}")
	private String openApiUrl;

	@Value("${openapi.servicekey}")
	private String serviceKey;

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

	// 월별 로그인 수
	@Override
	public StatisticDto.Result getMonthLogin(String year) {

		String full_year = year.substring(2, year.length());

		// 월별 로그인 수
		List<StatisticDto.MonthDto> findStatic = uMapper.selectMonthLogin(full_year);

		return new StatisticDto.Result<>("true", Integer.parseInt(year), findStatic);
	}

//	일별 로그인 수
	@Override
	public StatisticDto.Result getDayLogin(String year) {

		String short_year = year.substring(2, year.length());

		// 해당 연도에서 로그인 기록이 있는 중복제거한 월을 가져온다
		List<StatisticDto.DistinctMonthDto> distinctMonthDto = uMapper.selectDistinctMonth(short_year);

		List<StatisticDto.DayMonthDto> dayMonthDto = uMapper.selectDayLogin(short_year);
		
		List<StatisticDto.DistinctMonthDto> monthDto = new ArrayList<>();
		for (StatisticDto.DistinctMonthDto array : distinctMonthDto) {
			monthDto.add(new StatisticDto.DistinctMonthDto(array.getMonth(), find_day(array.getMonth(), dayMonthDto)));
		}

		return new StatisticDto.Result<>("true", Integer.parseInt(year), monthDto);
	}

	// 해당 월에 대한 일별 로그인 수를 가져온다
	private List<StatisticDto.DayDto> find_day(int month, List<StatisticDto.DayMonthDto> dayMonthDto) {
		
		List<StatisticDto.DayDto> dayDto = new ArrayList<>();
		for (StatisticDto.DayMonthDto array : dayMonthDto) {
			if (month == array.getMonth()) {
				dayDto.add(new StatisticDto.DayDto(array.getDay(), array.getLoginNum()));
			}
		}

		return dayDto;
	}

//	평균 하루 로그인 수
	@Override
	public HashMap<String, Object> getAvgDayLogin(String year) {
		HashMap<String, Object> map = new HashMap<>();

		String short_year = year.substring(2, year.length());

		map = uMapper.selectAvgDayLogin(short_year);
		map.put("year", year);
		map.put("is_success", "true");
		return map;
	}

//	휴일을 제외한 로그인 수
	@Override
	public HashMap<String, Object> getExceptHolidayLogin(String year) throws Exception{
		
		// XML로 파싱하기
//		parse_xml(year);

		String short_year = year.substring(2, year.length());
	
		// JSON으로 파싱하기
		String[] holiday_list = parse_json(year);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("year", short_year);
		map.put("holiday", holiday_list);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result = uMapper.selectExceptHolidayLogin(map);
		result.put("is_success", "true");
		
		return result;

	}

	private String[] parse_json(String year) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(openApiUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /* 연 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* json으로 출력 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /* 한페이지에 30개 가져오기 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader bf;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			bf = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		bf.close();
		conn.disconnect();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		JSONObject response = (JSONObject) jsonObject.get("response");
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray item = (JSONArray) items.get("item");

		String[] holiday_list = new String[item.size()];
		
		for (int i = 0; i < item.size(); i++) {
			JSONObject item_list = (JSONObject) item.get(i);
			String holiday = item_list.get("locdate").toString();
			
			// 월, 일을 구한다.
			holiday = holiday.substring(2, 8);
			holiday_list[i] = holiday;
		}
		
		return holiday_list;

	}

	private void parse_xml(String year) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(openApiUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /* 연 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* json으로 출력 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /* 한페이지에 30개 가져오기 */

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(urlBuilder.toString());

		// root tag
		document.getDocumentElement().normalize();
		System.out.println("Root Element: " + document.getDocumentElement().getNodeName());

		// 파싱할 tag
		NodeList nList = document.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 :" + nList.getLength());

		List<String> holiday = new ArrayList<String>();

		// list에 담긴 데이터 출력
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElemnt = (Element) nNode;
				System.out.println("명절이름 : " + getTagValue("dateName", eElemnt));
				System.out.println("날짜 : " + getTagValue("locdate", eElemnt));

				holiday.add(getTagValue("locdate", eElemnt));
			}

		}
	}

	private static String getTagValue(String tag, Element eElement) {

		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);
		if (nValue == null) {
			return null;
		}

		return nValue.getNodeValue();

	}

//	부서별, 월별 로그인 수
	@Override
	public Result getDeptMonthLogin(String year) {
		
		String short_year = year.substring(2, year.length());

		List<HashMap<String, String>> map = uMapper.selectDistinctDept();
		
		List<StatisticDto.DeptMonthDto> deptMonthDto = uMapper.selectDeptMonthLogin(short_year);
		
		List<StatisticDto.DeptDto> result = new ArrayList<StatisticDto.DeptDto>();
		for(int i =0 ; i < map.size(); i++) {
			
			for( Entry<String, String> entry : map.get(i).entrySet()) {
				result.add(new StatisticDto.DeptDto(entry.getValue(), find_month(entry.getValue(), deptMonthDto)));
			}
		}		
		return new StatisticDto.Result<>("true", Integer.parseInt(year), result);
	}
	
	private static List<StatisticDto.MonthDto> find_month(String dept, List<StatisticDto.DeptMonthDto> deptMonthDto){
		
		List<StatisticDto.MonthDto> monthDto = new ArrayList<StatisticDto.MonthDto>();
		for(StatisticDto.DeptMonthDto array : deptMonthDto) {
			if(dept.equals(array.getDept())) {
				monthDto.add(new StatisticDto.MonthDto(array.getMonth(), array.getLoginNum()));
			}
		}
		
		return monthDto;
		
	}

}
