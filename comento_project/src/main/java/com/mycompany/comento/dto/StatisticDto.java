package com.mycompany.comento.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatisticDto {
	
	private int requestID;
	private String requestCode;
	private String userID;
	private int createDate;

	// 총 합계
	private int totCnt;
	// 로그인 수
	private int loginNum;
	// 월별 접속자
	private String month;
	// 일별 접속자
	private int day;
	
	
	@Data
	@AllArgsConstructor
	public static class Result<T> {
		private int totCnt;
		private int year;
		private T data;
	}
	
	// 월별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	public static class MonthDto {
		private int month;
		private int loginNum;
	}

	// ------- 일별 로그인 수 DTO --------
	@Data
	@AllArgsConstructor
	public static class DayMonthDto {
		private int month;
		private List<DayDto> data;
	}

	@Data
	@AllArgsConstructor
	public static class DayDto {
		private int day;
		private int loginNum;
	}

	
	
}
