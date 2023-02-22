## 📋 개요

> http://narlae0.ddns.net:8080
>
- 기존 팀프로젝트 onlyfresh를 새 기술로 DB구축부터, 개발, 배포한 개인 프로젝트



## 🛠️ 기술 스택


JAVA 11, Spring Boot 2.7.5, Spring Security, Spring Data Jpa, MariaDB, Javascript, thymeleaf

 


## 📝 구현 기능 명세

>개인 pc에 docker로 mariaDB 구축

- Jar파일을 이미지화, 컨테이너를 pc에서 실행

>로그인 Authentication, 페이지 Authorization 

- Spring security 기반으로 jwt 도입
- 쿠키에 access, refresh token가 httponly로 저장
- filter와 manager 등은 custom 적용
> 상품 페이징, 상품 정렬, 카테고리별 분류

<img src="https://user-images.githubusercontent.com/107486308/220676634-d8c2af0b-b399-4cee-ba63-a1f4da895d76.gif">

>상품별 리뷰 게시판, 추천 및 정렬 페이징
- 비동기적인 게시판

<img src="https://user-images.githubusercontent.com/107486308/220677242-d440c7ef-5012-4a3b-891f-eefb7efc008d.gif">

>장바구니, 주문 및 결제, 날짜별 결제내역, 결제상세

<img src="https://user-images.githubusercontent.com/107486308/220677882-8a99c174-fb16-470d-8321-01516686c603.gif">

>주소지 등록 및 변경 삭제

<img src="https://user-images.githubusercontent.com/107486308/220677913-1cd907a0-a7cf-487e-8747-3402c81104fe.gif">


