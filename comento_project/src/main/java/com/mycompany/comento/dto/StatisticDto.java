package com.mycompany.comento.dto;

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
	

	
	
}
