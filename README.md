# comento-project
## 일시: 2021/09/02 - 2021/09/07 - 1주차

### 주간보고

1. 품질데이터 관리 운영
    + 통계 API 구축
      + 사용자 로그인 현황 및 대상 항목 업무 검토 (진행)
      + 개발환경 셋팅 (완료)

2. 개발환경 세팅

| 도구 | 버전 |
|:--------|:-------|
| Framework | Spring 5.3.9 |
| IDE | Eclipse |
| JDK | JDK 1.8 |
| DB | MySQL 8.0.2 |
| Build Tool | Maven |

+ 스프링 부트 환경 검토
  + 스프링 부트 마이그레이션에 따른 영항도 검토

#### 진행 결과
<image src="https://user-images.githubusercontent.com/56327398/133193078-84ea9d70-8c28-4afb-bee8-d809a35d5a85.png"  width="500px"/>

---
## 일시: 2021/09/08 - 2021/09/14 - 2주차

### 주간보고

1. 품질데이터 관리 운영
    + 통계 API 구축
        + 사용자 로그인 현황 및 대상 항목 업무 검토 (진행)
        + HTTP 통신 관련한 학습 (완료)
        + API가이드 문서 작성(완료)

2. SW활용 현황 API
    + 월별 접속자 수
    + 일자별 접속자 수
    + 평균 하루 로그인 수
    + 휴일을 제외한 로그인 수
    + 부서별 월별 로그인 수



#### 진행결과

|||
|---|----|
|![image](https://user-images.githubusercontent.com/56327398/133193526-b8b70c3f-2351-43ba-b7e4-ff6bd2984ae0.png)|![image](https://user-images.githubusercontent.com/56327398/133193575-70990a6e-b871-402b-8c07-cb3d1d25471f.png) |

### HTTP 학습내용
1) HTTP 통신과 과정
https://velog.io/@sloth/HTTP-%ED%86%B5%EC%8B%A0 
2) HTTP 메시지 및 메서드
https://velog.io/@sloth/HTTP-%EB%A9%94%EC%8B%9C%EC%A7%80-%EB%B0%8F-%EB%A9%94%EC%84%9C%EB%93%9C
---
## 일시: 2021/09/15 - 2021/02/22 - 3주차

### 주간보고

1. 품질데이터 관리 운영
    + 통계 API 구축
        + API구축을 위한 스프링부트 세팅 및 SQL작성

2. 개발환경 세팅

| 도구 | 버전 |
|:------|:--------|
| Framework | Spring Boot 2.5.5 |
| IDE | Eclipse |
| JDK | 1.8 |
| DB | MySQL 8.0.2 |
| Build Tool | Maven |
```java
	<select id="selectYearLogin" parameterType="string" resultType="hashMap">
		select count(*) as totCnt
		from statistic.requestinfo ri
		where left(ri.createDate, 2) = #{year};
	</select>
	
	<!-- 해당연도 총 접속자 수 -->
	<select id="selectYearTotLogin" parameterType="string" resultType="int">
		select count(*) as totCnt from statistic.requestinfo ri where left(ri.createDate, 2) = #{year};
	</select>
	
	<!-- 해당 월 총 접속자 수 -->
	<select id="selectMonthTotLogin" parameterType="string" resultType="int">
		select count(*) as totCnt from statistic.requestinfo ri where left(ri.createDate, 4) = #{yearMonth};
	</select>
	
	<!-- 월별 접속자 수 -->
	<select id="selectMonthLogin" parameterType="string" resultType="StatisticDto" >
		select mid(ri.createDate, 3, 2) as month, count(*) as loginNum
		from statistic.requestinfo ri 
		where left(ri.createDate, 2) = #{year} group by mid(ri.createDate, 3, 2);
	</select>

	
	<!-- 일자별 접속자 수 -->
	<select id="selectDayLogin" parameterType="string" resultType="StatisticDto">
		select mid(ri.createDate, 5, 2) as day, count(*) as loginNum
		from statistic.requestinfo ri
		where left(ri.createDate, 4) = #{yearMonth}
		group by mid(ri.createDate, 5, 2);
	</select>
	
	<!-- 하루 평균 로그인수-->
	<select id="selectAvgDayLogin" parameterType="string" resultType="hashMap">
		select count(*)/365 as totCnt
		from statistic.requestinfo ri
		where left(ri.createDate, 2) = #{year}
	</select>
	
	<!-- 휴일을 제외한 해당연도 로그인 수 -->
	<!-- 현재는 휴일을 포함 -->
	<select id="selectExceptHolidayLogin" parameterType="string" resultType="hashMap">
		select count(*) as totCnt
		from statistic.requestinfo ri
		where left(ri.createDate, 2) = #{year}
	</select>
	
	<!-- 해당연도 부서별, 월별 로그인 수 -->
	<select id="selectDeptMonthLogin" parameterType="string" resultType="hashMap">
		select requestCode as dept, mid(ri.createDate, 3, 2) as month, count(*) as loginNum 
		from statistic.requestinfo ri
		where left(ri.createDate, 4) = #{yearMonth}
		group by requestCode, mid(ri.createDate, 3, 2);
	</select>
```
* 휴일을 제외한 해당연도 로그인 수는 Open Api를 이용할 예정
