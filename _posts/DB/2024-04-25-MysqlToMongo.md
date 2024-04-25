---
title: "Mysql to Mongo - mysql 데이터 mongodb로 옮기기"
category: db
layout: post
---

## 1. Mysql 데이터 json 형식으로 export

어떤 데이터베이스 관리 도구를 사용하던 데이터를 export하는 기능은 탑재되어 있을 것입니다. 저는 mysql 설치시 기본으로 깔리는 workbench를 기준으로 설명합니다.

<img src="/assets/image/mongo1.png">
저는 간단하게 select문을 통해 테이블을 모두 조회한 후 조회 결과 바로 위에 있는 Export/Import: 플로피디스크 아이콘을 클릭해 데이터를 json 형식으로 내보냈습니다.

## 2. MongoDB 데이터베이스 툴 다운로드

아마 몽고db 4 버전까지는 기본으로 제공했던 것으로 보이는데 이제는 deprecated된 기능을 사용하기 위해 아래 링크를 타고 MongoDB Command Line Database Tools Download 부분 파일을 다운로드해줍니다.

https://www.mongodb.com/try/download/database-tools


그러면 mongodb-database-tools-windows-x86_64-100.9.4 이런 형태의 폴더가 생기고 해당 폴더 하위에 있는 bin 폴더에 mongoimport.exe라는 실행 파일을 확인할 수 있습니다. 해당 명령어를 사용하기 위해서 시스템 환경 변수 설정에 추가합니다.

**시스템 환경 변수 편집 > 환경 변수 > 시스템 변수/Path > 위에서 다운로드한 bin폴더 경로 추가 후 확인 버튼**

## 3. mongoimport 명령어로 json데이터 전송

```
mongoimport -c=컬렉션명(테이블명) -d=데이터베이스명 --file=내가export한mysql데이터.json --jsonArray
```

완료