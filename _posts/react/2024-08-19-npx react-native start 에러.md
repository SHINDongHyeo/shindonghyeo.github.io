---
category: react
title : "npx react-native start 에러 ( unknown command 에러 해결하기 )"
layout: post
---

## 에러 상황

`npx react-native init 프로젝트명` 명령어를 통해 프로젝트 생성 후 테스트를 위해 실행하려 해당 프로젝트에 들어가서 아래 명령어 실행

`npx react-native start`

하지만 unknow command 에러가 발생


## 해결

버전 문제일 가능성이 큼.

node_moduels, package-lock.json 파일을 삭제 후 다시 npm install 명령어를 통해 관련 라이브러리 재설치로 해결