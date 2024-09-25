---
category: docker
title: Dockerfile npm install 명령어가 있음에도 node_modules가 없는 경우
layout: post
---

참고자료 : https://stackoverflow.com/questions/30043872/docker-compose-node-modules-not-present-in-a-volume-after-npm-install-succeeds


## 상황
항상 동일한 패키지들을 사용하기 위해 Dockerfile에서 `RUN npm install` 혹은 `RUN npm ci` 명령어를 사용했다. 실제로 docker-compose 명령어를 이용해 여러 도커 이미지를 빌드하는 로그에도 해당 명령어가 실행됨을 확인했다.

하지만 실제로 도커 컨테이너가 생성되고 해당 컨테이너에 접속해보면 node_modules가 존재하지 않아 어플리케이션을 실행하는 과정에 에러가 발생함을 알 수 있었다.

## 이유 : volumes 설정

나의 경우 volumes 설정이 문제였다. 나의 docker-compose.yml 설정 파일은 다음과 같다.

```
# docker-compose.yml 파일 중 일부

services:
    auth:
        container_name: auth
        build:
            context: ./forcommuter_auth
            dockerfile: Dockerfile
        image: auth
        environment:
            - TZ=Asia/Seoul
        env_file:
            - ./forcommuter_auth/.env.development
        volumes:
            - ./forcommuter_auth:/usr/src/app
        restart: on-failure
        stdin_open: true
        tty: true
        depends_on:
            - db
```

volumes 설정을 보면 현재 경로에 있는 forcommuter_auth라는 폴더의 파일들을 /usr/src/app(워킹디렉토리로 설정한 경로)에 마운트하도록했다. 이 설정은 넣은 이유는 내 어플리케이션 소스 코드를 forcommuter_auth 파일에 넣어 관리하고 있었기 때문이다.

원래는 완전히 어플리케이션 개발이 완료되면 해당 소스 코드를 모두 담은 도커 이미지를 만드는 것이 일반적이라고 알고 있다. 하지만 나의 경우 현재 개발이 완료되지 않아 테스트할 때마다 도커 이미지를 만드는 것이 비효율적이라고 생각해 위와 같이 호스트에 소스 코드를 배치하고, 도커 컨테이너를 띄울 때는 volumes 설정을 통해 도커 컨테이너 워킹 디렉토리에 마운트한 것이다.


여기서 문제가 생겼다. volumes 설정은 보통 도커 이미지를 빌드할 때 적용되지 않고 그 후에 적용되는 것 같다. 즉, 도커 이미지를 빌드하는 과정에 Dockerfile에서 설정한대로 npm install이나 npm ci 명령어로 node_modules 디렉토리가 생겼더라도 결국 도커 이미지 빌드 이후 volumes 설정에 따라 마운트가 되는 과정에 node_modules가 덮어쓰기로 사라지는 것이다.(호스트 경로에는 node_moduels가 없었으니까!)


## 해결방법

즉, 간단하게 volumes 설정이 node_modules를 덮어쓰지 않게 수정하면 된다.  