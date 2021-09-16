# Nas-api
## 프로젝트 환경 
- Spring Boot 2.4.4
- Spring Security
- Json Web Token (JWT) 
- MyBatis 2.1.1
- MariaDB : 개인 NAS 활용(NAS mariaDB version: 10.3.10-MariaDB-1:10.3.10+maria~bionic)
- Swagger 2.9.2

## 참고사항
1. 회원 로그인 인증
- 회원 로그인 인증 방식은 JWT 토큰으로 설정 
- 로그인 성공 후, 발급받은 토큰은 별도로 client에서 보관하고 API 조회시 Header에 셋팅하여 호출
- accessToken 만료시, refreshToken으로 재발급
2. 데이터베이스 관련 
- 개인 NAS 에 설정된 mariaDB를 사용하여 작업을 진행 
- Master용 DB, Slave용 DB를 별도 구성하지 않고 한개의 DB로 master/slave로 dataSource 분리하여 처리 
- @Master/@Slave 로 어노테이션 기준으로 처리 되었으며, @Slave 에 연결된 계정은 읽기 권한만 존재한다고 가정
- resource > import, schema.sql
3. 유효성 검사
- 요청 파라미터에 대한 유효성(빈값,길이 등)은 @Valid를 통해 진행 
- 비즈니스 로직에 대한 유효성은 별도 Exception을 생성하여 처리

## Swagger UI
- http://localhost:8080/swagger-ui.html
- 해당 페이지에서 API 정보를 확인하고, 호출하여 테스트 진행.

## IntelliJ .http file 
- 인텔리제이를 사용할 경우, 손쉽게 Run 하여 실행.
- 서버 구동 후, idus-api > http > *.http 우클릭 하여 테스트 진행

## Test
- 정규식 테스트
