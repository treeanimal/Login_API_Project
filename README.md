# comento-project
## 일시: 2021/09/02 - 2021/09/07 - 1주차

### 주간보고

1. 품질데이터 관리 운영
    + 통계 API 구축
      + 사용자 로그인 현황 및 대상 항목 업무 검토 (진행)
      + 개발환경 셋팅 (완료)

2. 개발환경 세팅

|도구|버전|
|--------|-------|
|Framework|Spring 5.3.9|
|IDE|Eclipse|
| JDK | JDK 1.8 |
| DB | MySQL 8.0.2 |
| Build Tool | Maven |

+ 스프링 부트 환경 검토
  + 스프링 부트 마이그레이션에 따른 영항도 검토

---
## 일시: 2021/09/08 - 2021/09/14 - 2주차

### 주간보고

1. 품질데이터 관리 운영
    + 통계 API 구축
        + 사용자 로그인 현황 및 대상 항목 업무 검토 (진행)
        + HTTP 통신 관련한 학습 (완료)
        + API가이드 문서 작성(완료)

#### 1) HTTP 통신이란?
- 'HyperText Transfer Protocol'의 약자로 HTML 파일을 전송하는 프로토콜이라는 의미.
- 하지만 현재는 JSON, Image 파일 등 다양한 형태로도 전송이 가능하다.

#### 2) HTTP 통신의 통신 방식
![image](https://user-images.githubusercontent.com/56327398/133175585-31d4d425-6be7-414f-8089-9000af829946.png)<br>
<출처: https://velog.io/@sparklingwater/HTTP >

- HTTP 통신은 클라리언트에서 서버로 요청(request), 서버에서 응답 결과(response)를 보낸다. 
- 서버는 클라이언트의 요청이 있을 경우에만 응답을 하는데, 이것을 단방향 통신이라고 한다.
- HTTP는 비연결성(Connectionless) 를 가진다.
  - 비연결성이란? 클라이언트와 서버간의 한 번의 연결 후 맺어있던 연결을 끊는 것
  - 장점: 연결을 계속 유지한다면, 이에 따른 많은 리소스를 발생하므로 이것을 줄일 수 있다.
  - 단점: 매번 요청할 때마다 새로운 연결을 해야하기 때문에 연결/해제에 대한 오버헤드가 발생할 수 있다.
    - 그래서 이것에 대한 해결책으로 KeepAlive 속성이 생겨났다.
    - 이것은 지정된 시간동안 서버와 클라이언트 사이에서 패킷 교환이 없을 경우, 상대방의 상태를 묻기 위한 패킷을 주기적으로 보낸다.
    - 이 때 패킷의 반응이 없으면 접속을 끊게 된다.
- HTTP는 무상태(Stateless)이다.
  - 무상태란? 비연결성(Connectionless)로 인해 서버는 클라이언트가 누구인지 식별할 수 없는데, 이것을 무상태라고 한다.
  - 그래서 클라이언트를 기억하는 방법으로 쿠키, 세션, 토큰 등을 이용한다.

#### 3) 브라우저에 URL입력 후 요청하여 서버에서 응답하는 과정
![image](https://user-images.githubusercontent.com/56327398/133179831-6ba4a632-43dc-4961-8ee4-123800c0a7e5.png) <br>
<출처: http://tcpschool.com/webbasic/works >

#### 과정
1. 웹 브라우저에 URL을 입력하고 엔터를 누른다.
2. 브라우저에서 URL을 파싱하여 구조를 해석합니다.<br>
 2-1 URL이 구조에 맞지 않다면? 사용 중인 웹 브라우저의 기본 검색엔진으로 입력어를 검색한다.<br>
 2-2 URL이 구조에 맞다면? Punycode 인코딩을 url의 호스트 부분에 적용시킨다.
    * Punycode란? 한글 도메인을 사용할 경우 DNS가 한글을 처리하기 위하여 영어, 숫자, 하이픈(-)으로 이루어진 Punycode로 변환 하는 것<br>
      * EX) www.긴급재난지원금.kr -> www.xn--jjobb2kr6h965bxcbp8g.kr 
      * 그 후 DNS 서버에 전달 및 저장처리를 한다.
4. HSTS (HTTP Strict Transport Security) 목록을 로드해서 확인한다.
    * HSTS(HTTP Strict Transport Securitiy)란? 취약점이 많은 HTTP를 통하여 요청이 들어오면 강제적으로 302 Redirect를 이용하여 HTTP의 취약점을 보완한 HTTPS로 전환시키는 것.
    * HSTS Preload List란? HSTS가 적용된 웹 사이트의 명단을 모아둔 리스트 정보이다. 사이트 운영자는 자신이 운영하는 웹 사이트에 HTTPS 적용을 완료하고 HSTS Preload List에 등록할 수 있는 가이드를 준수하면 자동으로 등록된다.
5. DNS를 통하여 URL을 IP주소로 변환
    * DNS 요청을 보내기 전 브라우저에 해당 도메인이 캐시되어있는지 확인하고 있으면 그 IP 주소를 리턴한다.
    * 없을 경우 DNS 시스템이 따라, Root server - TLD server - Authoriative server에 IP 주소를 물어본다.
6. ARP를 이용하여 DNS로 부터 전달받은 IP주소를 이용하여 상대방의 MAC주소를 추적한다.<br>
  6-1 같은 랜 안의 모든 컴퓨터에게 메시지를 보낸다( "이 IP누구야? " )<br>
  6-2 그 IP가 내부에 있다면, 본인의 것이라고 응답한다.<br>
  6-3 만약 내부에 없다면? 라우터를 이용하여 목적지까지 가는 중의 다음 장비의 주소를 적는다. ( MAC Address에 게이트웨이를 담아서 외부로 던진다.)
    * ARP란? 주소 결정 프로토콜(Address Resolution Protocol) - 해당 IP를 그 IP주소에 맞는 물리적인 주소 즉, MAC주소를 가지고 오는 프로토콜.
7. 대상 서버와 TCP 소켓 연결
    * 소켓 연결은 3-way-handshake과정을 통해 이루어진다.
    * HTTPS의 경우 암호화 통신을 위한 TLS핸드쉐이킹이 추가된다.
    ![image](https://user-images.githubusercontent.com/56327398/133185561-34884fd6-2a1e-4009-9b81-7d06f8c2dd29.png) <br>
    <출처: https://mindnet.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%89%BD%EA%B2%8C-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-22%ED%8E%B8-TCP-3-WayHandshake-4-WayHandshake >
8. HTTP(HTTPS)프로토콜로 요청, 응답
  * 연결이 되었으니 서버에게 해당 URL을 요청하고 요청에 따른 컨텐츠를 불러온다.
9. HTML, CSS, JS등으로 이루어진 화면을 출력한다.

