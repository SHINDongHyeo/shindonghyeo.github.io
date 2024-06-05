---
category: javascript
title : "[express js] app.use의 의미와 위치에 따른 적용 순서 차이"
layout: post
cover : /assets/image/nodejs.png
---

## app.use란?

```javascript
const express = require('express');
const app = express();
```

위와 같은 코드를 통해 express js를 시작하는 일반적인 경우 개발을 진행하다보면 라우터에 설정한 경로를 적용하거나 DB를 이용하기 위해 mysql 패키지를 require한 후 해당 패키지를 사용하기 위해
'app.use'라는 명령어를 자주 사용하게 된다. 이때 app.use란 무슨 역할일까?


app.use는 간단하게 미들웨어 함수를 express js 어플리케이션에 적용하기 위해 사용되는 메서드다.

### app.use 예시
다음과 같이 많은 형태로 app.use 메서드는 사용될 수 있다.

- 정적 파일 서빙
- 서드 파티 미들웨어 사용
- router 경로 적용
- 직접 만든 미들웨어 사용

```javascript
// 정적 파일 서빙 : web이라는 디렉토리 하위에 정적 파일이 존재한다는 뜻
app.use(express.static('web'));

// 서드 파티 미들웨어 사용 : express js에서 sesson을 사용하는 과정
const session = require('express-session');
const RedisStore = require('connect-redis')(session);
app.use(session({
        // 세션 옵션
    }));

// router : ./router/login.js 에서 express.Router()객체를 선언 후 해당 객체에 설정한 get, post 요청 등을 /login 경로를 prefix로 설정한다는 뜻
app.use('/login', require('./router/login'));


// 나만의 미들웨어 함수 구현 : 로그인 여부 검사용 미들웨어로 session정보에 내가 원하는 값들 있으면 넘어가기(next();), 없으면 '/login' 경로로 리다이렉트
function requireLogin(req, res, next) {
    if (req.session && req.session.userinfo) {
            next();
    } else {
        res.redirect('/login');
    }
}
// 내가 만든 requireLogin 미들웨어 사용
app.use(requireLogin);
```


### app.use 위치와 순서


이때 중요한 것 중 하나가 app.use가 선언되는 위치다. app.use는 순차적으로 실행되기 때문이다!

예를 들면 위에서 제공한 예시 중 requireLogin이라는 함수를 만들고 이를 미들웨어로 사용하는 경우, 'app.use(requireLogin)' 코드보다 위에 작성된 코드에는 해당 미들웨어가 적용되지 않는다.
즉, 해당 미들웨어가 적용되는 부분은 해당 코드보다 아래 작성된 부분만이다.

그래서 만약 모든 페이지에 대한 로그인 여부 확인을 하기 위해서는 다음과 같이 작성해야 한다.

```javascript
// 나만의 미들웨어 함수 구현 : 로그인 여부 검사용 미들웨어로 session정보에 내가 원하는 값들 있으면 넘어가기(next();), 없으면 '/login' 경로로 리다이렉트
function requireLogin(req, res, next) {
    if (req.session && req.session.userinfo) {
            next();
    } else {
        res.redirect('/login');
    }
}
// 내가 만든 requireLogin 미들웨어 사용
app.use(requireLogin);

app.use('/home', require('./router/home'));
app.use('/news', require('./router/news'));
...
```

만약 'app.use('/home', require('./router/home'));' 코드를 'app.use(requireLogin);' 코드 전에 작성했다면 /home 경로로 접속할 때는 로그인 여부를 확인하지 않게 된다.