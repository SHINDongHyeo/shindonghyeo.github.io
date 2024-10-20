---
category: infosec
title: "SQL Injection 실습해보기(공격, 방어)"
layout: post
---

## SQL Injection이란?

SQL 인젝션(SQL Injection)은 웹 애플리케이션에서 DB를 이용할 때 SQL을 이용한다는 점을 활용한 공격 방법입니다. 사용자 입력을 적절히 검증한다면 발생하지 않을 수 있지만, 그렇지 않을 경우 발생하는 보안 취약점 중 하나입니다. 공격자는 이 취약점을 악용해 정상적인 SQL 쿼리에 악의적인 `SQL 코드를 주입`하여 데이터베이스를 조작하거나 비인가된 데이터를 탈취할 수 있습니다. 아래는 쉬운 예제입니다.

```sql
SELECT * FROM users WHERE username = 'admin' AND password = 'password';
```

웹 애플리케이션 서버에서 위와 같은 SQL을 통해 로그인 기능을 구현했다고 가정합니다. 이때 사용자가 로그인을 하기위해 username으로 admin이라는 문자열과 password에는 password라는 문자열을 입력합니다. 이는 개발자가 예상한 사용자의 접근 방식이고 이를 통해 회원가입 여부를 체크할 수도 있고, DB에 일치하는 계정 정보가 있는지도 파악할 수 있습니다.

<U>하지만 이때 사용자가 username에 `admin' -- `이라는 문자열을 입력하면 어떻게 될까요?</U>

```sql
SELECT * FROM users WHERE username = 'admin' -- ' AND password = 'password';
```

이런식으로 SQL 뒤쪽 부분이 모두 주석처리가 되어버렸기 때문에, 단순히 username이 admin인 데이터를 조회하도록 되어있습니다. <U>즉. 비밀번호를 몰라도 로그인이 되어 버리는 문제가 발생하는 것입니다.</U>

## 그럼 SQL Injection은 굉장히 위험한 공격인가?

개인적으로 아니라고 생각합니다. 왜냐하면 요즘 개발에서는 쌩 SQL 쿼리를 이용하는 경우는 적다고 생각하기 때문입니다. 요즘 개발에는 보통 DB를 이용하기 위해 ORM을 사용하는 경우가 많습니다. JPA, TypeORM, SQLAlchemy 등의 유명한 ORM에서는 보통 SQL Injection은 기본적으로 방어해주기 때문에 ORM을 사용해 개발할 때는 SQL Injection을 크게 신경쓰지 않아도 되는 장점이 있습니다.

또한 Prepared Statements (Prepared Queries)을 활용해도 이를 간단히 방어할 수 있습니다. 위 예시처럼 'admin' -- ' 이라는 값을 넣어도 해당 값은 통채로 문자열 처리되기 때문에 뒤에 있는 -- 부분이 SQL 주석 처리를 하지 못합니다.
(Prepared Statements는 SQL 쿼리를 미리 컴파일 하는 방식으로 위 예시 SQL은 이런 식으로 작성됩니다. SELECT \* FROM users WHERE username = ? AND password = ?;
) 그리고 사실 위에서 언급한 몇몇 ORM도 내부적으로 Prepared Statements를 이용해 SQL Injection을 방어하는 것으로 알고 있습니다.

<U>결과적으로 개발 과정에서 서버 코드에 SQL 쿼리를 직접 작성해서 이용하지 않으면 사실 웬만하면 SQL Injection은 방어됩니다.</U>

## SQL Injection 공격해보기

이때 위에서 설명했듯이 ORM을 사용하면 기본적으로 SQL Injection을 방어해줍니다. 하지만 ORM 사용 시에도 직접적인 쿼리를 짜는 메서드를 사용한다면 SQL Injection에 취약해집니다. 이를 테스트하기 위해 간단한 NestJS 프로젝트를 생성하고 아래와 같은 코드로 DB를 조회하는 API를 만들었습니다.

```typescript
// service 코드 중 일부
@Injectable()
export class AppService {
  constructor(private manager: EntityManager) {}

  async sqlInjectionTest(username, password) {
    return this.manager.query(
      `SELECT * FROM user WHERE username='${username}' AND password='${password}'`
    );
  }
}
```

즉, 사용자로부터 username과 password를 전달받아 이를 통한 sql 쿼리를 적용하는 얘시입니다. Postman을 통해 username에 admin이라는 문자열을 넣어주고 password에는 1234라는 문자열을 넣어 테스트한 결과 다음과 같이 데이터베이스에서 해당 조건에 부합하는 값을 확인할 수 있습니다.

```
[
    {
        "seq": 1,
        "username": "admin",
        "password": "1234"
    }
]
```

또한 typeorm logging 옵션을 통한 로그에서도 해당 값을 얻기 위한 쿼리를 확인할 수 있습니다.

```
query: SELECT * FROM user WHERE username='admin' AND password='1234'
```

하지만 이때 <U>SQL Injection 공격을 위해 username값으로 `admin ' -- ` 라는 값을 넣어보겠습니다.</U> 또한 password 값은 아예 보내지도 않았습니다. 하지만 결과적으로 같은 user 정보를 얻어낼 수 있었습니다. 또한 쿼리는 다음과 같이 표시됩니다.

```
[
    {
        "seq": 1,
        "username": "admin",
        "password": "1234"
    }
]
```

```
query: SELECT * FROM user WHERE username='admin' -- ' AND password='12345'
```

## SQL Injection 방어해보기

위에서 언급했듯이 ORM을 사용해 SQL Injection을 방어해봅니다.

```
  async sqlInjectionTest2(username, password) {
    return this.userRepository.find({
      where: {
        username: username,
        password: password,
      },
    });
  }
```

가장 간단한 typeorm을 이용한 DB 조회 방법입니다. 이 상황에서 SQL Injection을 시도하면 다음과 같은 결과와 로그를 확인할 수 있습니다,

```
[] // 비어있는 결과값
```

```
query: SELECT `User`.`seq` AS `User_seq`, `User`.`username` AS `User_username`, `User`.`password` AS `User_password` FROM `user` `User` WHERE ((`User`.`username` = ?)) -- PARAMETERS: ["admin' -- "]
```

이를 통해 SQL 자체가 주석 처리 되어버리는 SQL Injection 공격은 실패합니다.
