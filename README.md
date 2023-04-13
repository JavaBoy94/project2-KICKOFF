<p align="center">
  <img src="https://user-images.githubusercontent.com/116870617/231791531-9e7ee801-a462-4b7a-977d-1e56b195e28b.png">
</p>

# KICKOFF (킥오프)
## Open API와 챗봇을 활용한 동호회 운영/관리 그룹웨어 
## [프로젝트 소개 PDF](https://drive.google.com/file/d/18gtjqQk1KOKh-EcekBsUl4dJVbOpZ3JZ/view?usp=share_link)

## 🖥️ 프로젝트 소개 (2023.03.14 ~ 2023.04.06)
- Open API(우편번호, 날씨, 일정관리, 조직관리 등)를 활용한 동호회 운영/관리용 그룹웨어입니다.
- Spring MVC 패턴으로 개발하였습니다.
- 일정, 결재, 회계, 공지사항, 게시판 등 조직관리에 필요한 전반적인 기능들을 구현하였습니다.
- Komoran(한국어 형태소 분석기)을 활용한 시나리오형 챗봇을 구현하였습니다.
- Github Actions와 AWS EC2를 기반으로 CI/CD(지속통합/지속배포) 환경을 구축하였습니다.

## 🧑‍🤝‍🧑 팀 구성 및 역할
#### 👨‍💻팀장 : 김필수 <br>
#### java package : config / member / admin / entity / dto <br>
#### templates package : login / join / index / member / admin <br>
- 프로젝트 일정 관리 및 발표준비
- 소스 통합 및 형상관리
- 데이터 모델링 및 Entity, DTO 구현
- 회원관리 CRUD 구현
- 메인 페이지, 관리자 페이지 구현
- Spring Security, Oauth2 기반 로그인 및 회원가입 구현
- 날씨(Openweathermap), 우편번호(다음 우편번호) API 연동
- 결재서류 승인/반려 처리 구현 <br>

#### 👨‍💻팀원 : 김현기 <br>
#### java package : account / naver <br>
#### templates package : account / naver <br>
- 회계내역(수입/지출) CRUD 구현
- Naver API(workplace, work) 연동 <br>

#### 👨‍💻팀원 : 김홍록 <br>
#### java package : notice / board / comment <br>
#### templates package : notice / board <br>
- 공지사항, 커뮤니티 게시판 CRUD 구현
- 공통요소(fragment) 디자인
- Github Actions, AWS EC2 기반 CI/CD 구현 <br>

#### 👨‍💻팀원 : 이정모 <br>
#### java package : calender <br>
#### templates package : calender <br>
- 근태관리 구현
- 공식일정, 개인일정 CRUD 구현 
- 일정관리(fullcalender) API 연동 <br>

#### 👨‍💻팀원 : 장기운 <br>
#### java package : member / approval <br>
#### templates package : member / approval / management  <br>
- 결재관리 CRUD 구현
- 조직관리 CRUD 구현
- Komoran 기반 챗봇 구현 <br>

## ⚙️ 개발 환경
- Language : Java 11, HTML5, CSS3, JavaScript
- IDE : IntelliJ IDEA, Visual Studio Code
- Framework : Springboot
- Database : MySQL
- Template Engine : Thymeleaf 
- ORM : JPA <br>

