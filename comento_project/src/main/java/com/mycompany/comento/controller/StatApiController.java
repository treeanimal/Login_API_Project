package com.mycompany.comento.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.comento.dto.StatisticDto;
import com.mycompany.comento.service.StatisticService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class StatApiController {

	@Autowired
	private StatisticService service;

	@RequestMapping("/sqlyearStatistic")
	public Map<String, Object> sqltest(String year) throws Exception {

		return service.yearloginNum(year);
	}

	// 월별 로그인 수
	@GetMapping("/statisticLogin/month/{year}")
	public StatisticDto.Result getMonthLogin(@PathVariable String year) {

		return service.getMonthLogin(year);
	}

	// 일별 로그인 수
	@GetMapping("/statisticLogin/day/{year}")
	public StatisticDto.Result getDayLogin(@PathVariable String year) {

		return service.getDayLogin(year);

	}


	@GetMapping("/statisticLogin/avg/year/{year}")
	public Map<String, Object> getAvgLoginByYear(@PathVariable String year) {
		return service.selectAvgDayLogin(year);
	}

	@GetMapping("/statisticLogin/test1")
	public StringBuilder abc() throws Exception {


		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=yFZtYw82i34Si9efOl96ZDk69kYsYnwOk9K%2FOu9OmgzPcEGt6y9DH7tqm%2Bki0TUpNMVyVR7%2FDlORn5gZh6%2Bp2Q%3D%3D"); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode("2021", "UTF-8")); /* 연 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* json으로 출력 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /* 한페이지에 30개 가져오기 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		
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
		System.out.println(sb.toString());
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		JSONObject response = (JSONObject) jsonObject.get("response");
		JSONObject body = (JSONObject)response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray item = (JSONArray) items.get("item");
		
		for(int i = 0; i < item.size(); i++) {
			JSONObject item_list = (JSONObject) item.get(i);
			System.out.println("휴일날짜 : " + item_list.get("dateName"));
			System.out.println("날짜 : " + item_list.get("locdate"));
		}
		
		return sb;
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();
//		Document document = db.parse(urlBuilder.toString());
//
//		// root tag
//		document.getDocumentElement().normalize();
//		System.out.println("Root Element: " + document.getDocumentElement().getNodeName());
//		
//		// 파싱할 tag
//		NodeList nList = document.getElementsByTagName("item");
//		System.out.println("파싱할 리스트 수 :" + nList.getLength());
//
//		List<String> holiday = new ArrayList<String>();
//		
//		// list에 담긴 데이터 출력
//		for(int i = 0; i < nList.getLength(); i++) {
//			Node nNode = nList.item(i);
//			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
//				
//				Element eElemnt = (Element) nNode;
//				System.out.println("###########################");
//				System.out.println("명절이름 : " + getTagValue("dateName", eElemnt));
//				System.out.println("날짜 : " + getTagValue("locdate", eElemnt));
//				
//				holiday.add(getTagValue("locdate", eElemnt));
//			}
//			
//		}

	}

//	private static String getTagValue(String tag, Element eElement) {
//
//		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
//
//		Node nValue = (Node) nlList.item(0);
//		if (nValue == null) {
//			return null;
//		}
//
//		return nValue.getNodeValue();
//
//	}

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
	public static class MonthDto {
		private String month;
		private int loginNum;
	}

	// ------- 일별 로그인 수 DTO --------
	@Data
	@AllArgsConstructor
	static class DayMonthDto {
		private String month;
		private List<DayDto> data;
	}

	@Data
	@AllArgsConstructor
	public
	static class DayDto {
		private int day;
		private int loginNum;
	}

}
