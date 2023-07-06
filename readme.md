# 💊 포켓약사
시각장애인을 위한 의약품을 촬영하면 의약품 정보를 제공해주는 서비스
[발표자료 다운로드](https://drive.google.com/drive/folders/1NaPn1uhjQIofyPXDogXReBNUxxCRvm_B?usp=drive_link)

## 📌 인프라 구조
<img width="762" alt="image" src="https://github.com/pocket-yacsa/pocket-yacsa-server/assets/35839248/6c963dfd-d9ca-48e8-8456-0aa26d1e097b">

## 📌 ERD
<img width="500" alt="image" src="https://user-images.githubusercontent.com/35839248/230785036-e4551c00-5cf8-48da-8327-1fef216f9f60.png">

## 📌 UI
### 의약품 촬영
<img width="1000" alt="image" src="https://github.com/pocket-yacsa/pocket-yacsa-server/assets/35839248/c794f9f8-2341-43ae-9e07-269a6a083a37">

### 내 서랍 저장
<img width="1000" alt="image" src="https://github.com/pocket-yacsa/pocket-yacsa-server/assets/35839248/0bcc9176-5d85-4ced-bf8d-8abaf60fa974">

### 의약품 검색
<img width="1000" alt="image" src="https://github.com/pocket-yacsa/pocket-yacsa-server/assets/35839248/d1bfbc3b-1883-4ef0-825a-32f823193c7b">



## 📌 개발 이슈
- 약 5만개의 의약품 데이터베이스를 정형화 되지 않은 공공데이터들을 활용하여 Python pandas로 정제하여 구축
- ElasticSearch를 이용하여 의약품 검색엔진 구축
- Redis의 List를 이용하여 최근검색어 구현
- 구글 OAuth2 로그인 구현
- Junit5와 Mockito를 활용한 단위테스트 작성
- 의약품 조회 로직에 페이징을 적용
- Swagger를 이용한 API 문서 자동화
- Nginx와 certbot을 이용하여 Https 환경 구축

## 📌 사용 기술 및 환경
- Java 11
- Springboot 2.7.10
- Gradle
- Mysql 8
- ElasticSearch 7.14
- Redis 4.0.14
- JPA

