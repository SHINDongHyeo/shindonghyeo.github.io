# Mysql
## - 데이터베이스(DB)
<u>데이터베이스(DB : Database)는 데이터를 체계화해 통합,관리하는 데이터 집합체를 의미한다.</u> 즉, 효율적인 사용을 위해 처리된 데이터를 데이터베이스라고 생각할 수 있다. 이런 데이터베이스를 관리하는 프로그램을 데이터베이스 관리 시스템(DBMS : Database Management System)이라고 한다. 이런 DBMS 종류 중 지배적인 형태가 관계형 데이터베이스 관리 시스템(RDBMS : Relational Database Management System)이다. <u>RDBMS의 가장 큰 특징은 테이블이라는 구조를 이용해 데이터를 저장한다는 점이다.</u> 이런 RDBMS의 종류로는 Oracle, MySQL, MS-SQL, Maria DB 등이 있다.

## - 테이블(table)
RDBMS의 가장 큰 특징으로 키(key)와 값(value)의 관계를 나타낸 구조다.
- 열(column) : 필드(field)나 속성(attribute)이라고도 불리며 테이블에서 세로 한줄을 의미한다.
- 행(row) : 튜플(tuple)이나 레코드(record)라고도 불리며 테이블에서 가로 한줄을 의미한다.
- 키(key) : 기본 키(primary key)라고도 불리며 테이블에서 행의 식별자로 이용되는 열을 의미한다.
- 값(value) : 테이블에서 각각의 행과 열에 입력된 데이터를 의미한다.

## - 키(key)
키는 테이블에서 <u>레코드들(행들)을 서로 구분할 수 있게 하는 하나 이상의 속성들(열들)의 집합</u>을 의미한다. 쉬운 예로 주민등록번호같은 경우 개개인이 고유한 값을 가지고 있어 주민등록번호가 개개인을 구분할 수 있게하는 키(key)라고 할 수 있다.
- 후보 키(candidate key) : 기본 키가 될 수 있는 후보
    - 유일성 : 레코드(행)값들이 중복되지 않아야 함     
    (이름은 사람을 구분하는 데 유일성을 만족하지 않음 --> 동명이인 때문에)
    - 최소성 : 최소한의 키들만 포함해야 함      
    (주민등록번호는 사람을 구분하는 데 유일성을 만족하고, 운전면허등록번호도 사람을 구분하는 데 유일성을 만족한다. 이때 각각의 속성을 따로 키로 사용하면 최소성을 만족하지만, 이 둘을 합쳐 (주민등록번호,운전면허등록번호)를 하나의 키로 사용하게 되면 최소성을 만족하지 못하는 키가 된다.)

- 기본 키(primary key) : 후보 키들 중 선택된 단 하나의 키
    - not null : null값을 가지지 못함
    - 선언방법(6가지)
    ```
    -첫번째방법
    create table 테이블명(
        필드명 필드타입 primary key
    );

    -두번째방법
    create table 테이블명(
        필드명 필드타입,
        [constraint 제약조건명] primary key (필드명)
    );

    -세번째방법(새 필드 추가하면서 해당 필드를 기본 키로)
    alter table 테이블명 add 필드명 필드타입 primary key;

    -네번째방법(새 필드 추가하면서 해당 필드를 기본 키로)
    alter table 테이블명 add [constraint 제약조건명] primary key (필드명)

    -다섯번째방법(기존 필드 기본 키로 수정)
    alter table 테이블명 modify column 필드명 필드타입 primary key

    -여섯번째방법(기존 필드 기본 키로 수정)
    alter table 테이블명 modify column [constraint 제약조건명] primary key (필드명)
    ```
- 대체 키(alternate key) : 후보 키들 중 선택된 기본 키를 제외한 나머지 후보 키들
- 슈퍼 키(super key) : 유일성은 만족하지만 최소성은 만족하지 않는 키들
    - 유일성 : 레코드(행)값들이 중복되지 않아야 함     
    (이름은 사람을 구분하는 데 유일성을 만족하지 않음 --> 동명이인 때문에)
    - 최소성 : 최소한의 키들만 포함해야 함      
    (주민등록번호는 사람을 구분하는 데 유일성을 만족하고, 운전면허등록번호도 사람을 구분하는 데 유일성을 만족한다. 이때 각각의 속성을 따로 키로 사용하면 최소성을 만족하지만, 이 둘을 합쳐 (주민등록번호,운전면허등록번호)를 하나의 키로 사용하게 되면 최소성을 만족하지 못하는 키가 된다.)
- 외래 키(foreign key) : 하나의 테이블에서 참조하는 다른 테이블의 기본 키
    - 주민등록번호를 기본 키로 사용하는 테이블을 다른 테이블에서 참조하여 주민등록번호를 가져다 쓸 경우 이 속성을 외래 키라고 한다. 이때 참조 무결성을 지켜야 하므로 주민등록번호를 기본 키로 사용하던 테이블에 없던 주민등록번호 값을 참조할 순 없다. 오직 기본 키로 사용하던 테이블에 존재하는 값만을 참조할 수 있다.

## - SQL
Structured Query Language의 약어로 RDBMS를 이용하기 위해 제작된 언어이다. 다음과 같이 3가지로 분류할 수 있다.       
- DDL : Data Definition Language     
-> <u>골격 조작 용도</u>
    - create
    - alter
    - drop
    - truncate
- DML : Data Manipulation Language      
-> <u>내용 조작 용도</u>
    - select
    - insert
    - update
    - delete
- DCL : Data Control Language      
-> <u>권한 조작, 저장 조작 용도</u>
    - grant
    - revoke
    - commit
    - rollback




RDBMS(관계형 데이터베이스) 사용 언어로 table 구조를 가지고 table은 컬럼(속성)과 로우(레코드)로 구성되어 있다.

## 명령어
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

- 오라클과 달리 from없이 원하는 값을 검색해줄 수 있다.
    - select 2+3;    
    : 5 출력
    - abs(),round(),truncate(),mod() 등의 함수를 사용할 수 있다.
    - 몇가지 유용한 함수
    - case,when,end 함수     
    ```
    select 테이블속성,
	case
		when 조건1 then 작업1
		when 조건2 then 작업2
		when 조건3 then 작업3
		when 조건4 then 작업4
		else 테이블속성
	end as 테이블속성별칭
    from 테이블;
    ```
## 4. 그룹함수
그룹함수란 다수의 행데이터를 한번에 처리하는 함수를 의미한다. null값은 자동 제거해주며 기능한다.
- select count(테이블속성) from 테이블명;    
: 테이블 속에서 테이블속성 개수를 세고 출력한다.     
이를 이용해서 종류를 세주고 싶으면 distinct와 섞어서 'select count(dsitinct 테이블속성) from 테이블명'이런식으로 표현해줄 수 있다.
- select sum(테이블속성) from 테이블명;    
: 테이블 속에서 테이블속성을 모두 더해서 출력한다.
- select avg(테이블속성) from 테이블명;     
: 테이블 속에서 테이블속성의 평균을 구해서 출력한다.
- group by 테이블속성;      
: 테이블속성을 기준으로 그룹화
- having 조건문;      
: 그룹함수 사용 시 조건문