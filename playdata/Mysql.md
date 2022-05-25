# Sql
RDBMS(관계형 데이터베이스) 사용 언어로 table 구조를 가지고 table은 컬럼(속성)과 로우(레코드)로 구성되어 있다.

# 명령어
- database 명령어
    - show databases;   
    : 데이터베이스 목록 출력
    - create database 데이터베이스명;   
    : 데이터베이스 생성
- table 명령어
    - show table;   
    : 테이블 목록 출력  
    - create table 테이블명();  
    : 테이블 생성   
        - CONSTRAINT pk_테이블명 PRIMARY KEY ( 테이블속성 )     
        : 해당 테이블의 테이블속성이 중복되지 않는 존재로 설정
    - drop table 테이블명;  
    : 테이블 삭제   
    
- CRUD 명령어(insert/select/update/delete)
    - create database 데이터베이스명;   
    : 데이터베이스 생성
    - use 데이터베이스명;   
    : 데이터베이스 사용

이후 데이터를 생성하는 명령어도 있지만 먼저 데이터를 검색하는 기능부터 알아봄.

## 3. 검색
- select 테이블속성 as 표시할이름 from 테이블명;    
: 해당 테이블 속 테이블속성을 표시할이름으로 검색해 출력    
- select distinct 테이블속성 from 테이블명;     
: 해당 테이블 속 테이블속성에서 중복내용 제외하여 검색해 출력   
- select 테이블속성 from 테이블명 order by asc/desc;    
: 해당 테이블 속 테이블속성을 오름차순/내림차순으로 검색해 출력
- select 테이블속성 from 테이블명 where ( );    
: 해당 테이블 속 테이블속성에서 where 뒤 조건문을 만족하는 경우 검색해 출력     
    - 조건문에 들어갈 수 있는 내용  
        - null이나 int값을 비교할 때    
        : 테이블속성 is null/1/2/10 ... 
        - 문자열을 비교할 때    
        : 테이블속성 = "비교할 문자열"  
        - 범위를 지정할 때  
        : between 시작범위 and 끝범위
        - null체크  
        : IFNULL(테이블속성, null일경우출력할값)    
        - 대소문자 비교하기 위한 설정   
        : Binary("비교할 문자열")   
        - or 대신 쓸 수 있는 구조   
        : 테이블속성 in (비교할값1,비교할값2..)
