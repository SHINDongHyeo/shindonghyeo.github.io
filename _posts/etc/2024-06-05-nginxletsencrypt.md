---
category: etc
title : "Nginx와 Let's encrypt 설정하기"
layout: post
---

## 상황

Node.js를 통해 API 개발 완료

1. Nginx를 이용하고 있기 때문에 nginx 프록시 설정을 통해 nginx를 거쳐 해당 API 서버로 요청이 들어오게 설정해야함
2. https를 사용하기 위해 let's encrypt 설정이 필요함


일단 기본적으로 도메인은 구매한 상태라고 가정한다. 여기서는 **'mytest.com'**이라는 도메인 주소를 가지고 있다고 가정한다.


## Let's encrpyt 설정

1. Certbot 설치

```
sudo apt-get update
sudo apt-get install certbot
sudo apt-get install python3-certbot-nginx # nginx 플러그인
```

Certbot이란 Let's Encrypt의 공식 클라이언트다. 이를 통해 SSL/TLS 인증서를 발급하고 관리할 수 있다.


2. SSL/TLS 인증서 발급

```
sudo certbot certonly --nginx -d mytest.com -d www.mytest.com
```

-d 옵션은 디렉토리를 지정하는 명령어로 위 명령어를 해석하면 다음과 같다. Nginx 웹 서버에서 mytest.com 과 www.mytest.com이라는 도메인에 대한 SSL/TLS 인증서를 발급해라

3. 인증서 자동 갱신 설정

let's encrypt는 90일마다 만료된다. 하지만 자동 갱신을 지원하므로 자동 갱신 설정을 통해 사실상 무제한으로 이용이 가능하다.

```
sudo crontab -e
```

아래 줄 추가

```
0 0 * * 1 /usr/bin/certbot renew --quiet
```

위 설정은 매주 월요일 자정에 Certbot가 인증서를 갱신하도록 하는 설정이다. (--quiet은 출력 숨기는 옵션) 해당 날짜 간격은 개인마다 취향에 맞게 설정하기


## Nginx 설정
일반적으로 nginx 파일 설정은 다음과 같은 과정을 거친다.

1. sites-available 디렉토리 아래 파일 생성

일반적으로 sites-available 디렉토리 경로는 다음과 같다.

```
/etc/nginx/sites-available
```

해당 경로 아래에 파일을 생성한다. 보통 이때 파일명은 nginx 프록시 설정에 연결할 주소명을 사용한다.

```
/etc/nginx/sites-available/mytest.com
```

2. mytest.com 파일 내용 작성
위에서 생성한 sites-available/mytest.com 파일을 다음 예시와 같이 작성한다.

```
server {
    listen 80;

    server_name mytest.com www.mytest.com;

    location / {
        proxy_pass http://localhost:내포트번호;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-NginX-Proxy true;
        proxy_cache_bypass $http_upgrade;
        proxy_redirect off;
    }

    ssl_certificate /etc/letsencrypt/live/mytest.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/mytest.com/privkey.pem;

}

```

server라는 이름 아래에 몇 가지 옵션을 채운다고 생각하면 쉽다.
- server
   - listen
   nginx를 프록시 설정으로 할 것이므로 nginx의 포트인 80포트를 listen하겠다고 설정하면 됨
   - server_name
   사용할 도메인 이름을 설정하면 된다. 만약 내가 mytest.com이라고 입력하던 www.mytest.com이라고 입력하던 모두 같은 곳으로 안내하고 싶다면 위와 같이 두 도메인 명을 띄어쓰기를 통해 연달아 작성하면 된다.
   - location
   80포트로 들어오는 nginx로의 요청을 어디로 연결해주냐는 설정. proxy_pass에 대한 부분만 내가 현재 서버에서 실행해 놓은 어플리케이션의 포트번호를 넣으면 해당 어플리케이션으로 연결해줌.
   아래 proxy_thtp_version, proxy_set_header 등등의 옵션은 각자 목적에 따라 설정해도 되고 안해도 됨.
   - ssl_certificate
   https를 사용하기 위한 SSL/TLS 공개 인증서 파일 경로(위에서 설정한 경로)
   - ssl_certificate_key
   https를 사용하기 위한 SSL/TLS 개인 인증서 파일 경로(위에서 설정한 경로)




3. sites-enabled 하위에 심볼릭 링크 생성
심볼릭 링크는 윈도우에서 바로가기 아이콘이라고 생각하면 된다. 위에서 sites-available에 만들었던 파일의 바로가기를 만드는 것이다.

```
ln -s /etc/nginx/sites-available/mytest.com /etc/nginx/sites-enabled/
```

이 과정을 거치는 이유는 바로 nginx의 기본 설정 파일인 /etc/nginx/nginx.conf 파일에 sites-enableed 디렉토리 하위에 있는 모든 파일을 불러와 해당 설정을 이용하는 설정이 되어 있기 때문이다.

```
# /etc/nginx/nginx.conf 중
include /etc/nginx/sites-enabled/*;
```


4. Nginx 재시작

nginx 재시작을 통해 수정된 내용을 적용한다.

```
sudo systemctl restart nginx
```


