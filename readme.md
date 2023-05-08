# 💊 포켓약사
시각장애인을 위한 의약품을 촬영하면 의약품 정보를 제공해주는 서비스

## Swagger 명세서
- https://pocketyacsa.shop/swagger-ui.html

## 📌 인프라 구조
<img width="600" alt="image" src="https://user-images.githubusercontent.com/35839248/236770427-f40e9767-f67d-4e98-b2dc-473e376fb8db.png">

## 📌 ERD
<img width="500" alt="image" src="https://user-images.githubusercontent.com/35839248/230785036-e4551c00-5cf8-48da-8327-1fef216f9f60.png">

## 📌 UI
<img width="700" alt="image" src="https://user-images.githubusercontent.com/35839248/230785442-b1a0a4c1-df45-45de-b6f9-0c416f4a95d8.png">
<img width="700" alt="image" src="https://user-images.githubusercontent.com/35839248/230785395-7bcb68c4-c5b1-46c9-9ab5-afd1654480ce.png">
<img width="700" alt="image" src="https://user-images.githubusercontent.com/35839248/230785418-25abef44-082e-4c2c-92ae-b81ab8f285bc.png">
<img width="700" alt="image" src="https://user-images.githubusercontent.com/35839248/230785430-d38f69c9-b473-481e-888c-ad1c90f9f2c6.png">


## 📌 개발 이슈
- 약 5만개의 의약품 데이터베이스를 정형화 되지 않은 공공데이터들을 활용하여 Python pandas로 정제하여 구축
- ElasticSearch를 이용하여 의약품 검색엔진 구축
- Redis의 List를 이용하여 최근검색어 구현
- 구글 OAuth2 로그인 구현
- Junit5와 Mockito를 활용한 단위테스트 작성
- 의약품 조회 로직에 페이징을 적용
- Swagger를 이용한 API 문서 자동화

## 📌 사용 기술 및 환경
- Java 11
- Springboot 2.7.10
- Gradle
- Mysql 8
- ElasticSearch 7.14
- Redis 4.0.14
- JPA

