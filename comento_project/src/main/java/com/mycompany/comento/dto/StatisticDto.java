package com.mycompany.comento.dto;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class StatisticDto {
	
	@Data
	@AllArgsConstructor
	public static class Result<T> {
		private String is_success;
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

	// 일별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DistinctMonthDto {
		private int month;
		private List<DayDto> data;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DayMonthDto {
		private int month;
		private int day;
		private int loginNum;
	}
	
	@Data
	@AllArgsConstructor
	public static class DayDto{
		private int day;
		private int loginNum;
	}

	// 부서별, 월별 로그인 수 DTO
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DeptMonthDto{
		private String dept;
		private int month;
		private int loginNum;
	}
	
	@Data
	@AllArgsConstructor
	public static class DeptDto{
		private String dept;
		private List<MonthDto> data;
	}
	
	
}
