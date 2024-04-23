---
category: javascript
title : "[Express js] bodyparser 에러"
layout: post
cover : /assets/image/nodejs.png
---

## 상황

```
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended : true}));


...


app..post('/', function (req, res) {
        
        console.log(req.body.username);  ---> undefined
        console.log(req.body.password);  ---> undefined
        
    });

```
기존에는 위와 같은 방식의 body-parser 모듈을 통한 post 형식의 데이터를 받을 수가 있었습니다. 하지만 어느 순간 req.body에 담긴 데이터를 불러와도 undefined라고만 뜨며 post 데이터를 받지 못하는 상황이 생겼습니다.

## 해결방법

해당 오류는 express js 버전에 따른 문제입니다.

예전에는 express js에서 post 데이터를 다루기 위해 body-parser라는 모듈을 따로 설치했어야 했지만, express js 4 버전 이상에서는 해당 기능이 내장되어 제공되기 때문에 body-parser를 이용하면 안됩니다.

### 방법1. Express js 버전 다운그레이드
그러므로 Express js 버전을 4 버전 이하로 다운그레이드하면 body-parser를 이용하실 수 있습니다.

### 방법2. Express js 내장 기능 사용하기
아니면 Express js 4 버전 이상을 사용하시면서, 해당 버전에서 제공되는 기능을 사용하셔도 됩니다. 아래 코드를 통해 사용할 수 있습니다.

```
const express = require('express');
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
```