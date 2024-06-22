---
category: javascript
title : "[Nest.js]TypeORM - MySQL 에러 : Error: ER_NOT_SUPPORTED_AUTH_MODE: Client does not support authentication protocol requested by server; consider upgrading MySQL client"
layout: post
cover : /assets/image/nodejs.png
---

## 에러 메시지

`Error: ER_NOT_SUPPORTED_AUTH_MODE: Client does not support authentication protocol requested by server; consider upgrading MySQL client`

Nest JS에서 TypeORM을 이용해 MySQL 데이터베이스와 연결을 시도하는 과정에서 다음과 같은 에러가 발생했습니다. 이는 MySQL 8.0 버전 전후로 바뀐 mysql 인증 방법일 가능성이 높습니다.


## 해결하기

### 방법1. MySQL 인증 플러그인 변경

plugin을 `caching_sha2_password` --> `mysql_native_password` 방법으로 수정합니다.

```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
```

저는 로컬환경에서 접속을 테스트하고 있었기 때문에 'root'@'localhost'에 해당하는 부분을 바꿨습니다. 수정된 결과는 다음 sql문을 통해 확인할 수 있습니다.


```
select host, user, plugin from mysql.user;
```


### 방법2. MySQL 접속 가능한 Host 추가해주기

만약 본인이 MySQL이 위치한 주소와 다른 곳에서 접속을 시도하는 것이라면 MySQL에서는 기본적으로 localhost에서의 접속만 허용되어 있으므로 접속이 제한되는 상황일 수 있다.

아래와 같이 설정하면 외부에서부터의 접속을 모두 허용할 수 있다.

```
CREATE USER 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

이 또한 아래 명령어를 통해 수정된 결과를 확인할 수 있다.

```
select host, user, plugin from mysql.user;
```