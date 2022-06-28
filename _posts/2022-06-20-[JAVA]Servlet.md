---
author_profile: true
categories: "java"
toc: true
toc_sticky: true
---

# 1.Servlet이란?
서블릿이란 **자바를 사용하여 웹을 만들기 위한 기술**이다. 더 정확히 말하면 웹에서 발생되는 요청(Request)과 응답(Response)을 쉽게 관리할 수 있도록 해준다. 간단하게 웹을 이용해 유저와 소통하는 단계를 표현해본다.          
ex) 유저(웹) -> html/servlet/jsp(유저로부터 요청을 받음) -> 자바/DB (필요한 작업을 통해 데이터 가공) -> html/servlet/jsp(유저에게 응답을 보냄) -> 유저(웹)             

이런 특성으로 인해 **MVC패턴에서 Controller역할로 쓰이는 부분이 Servlet이다.**

# 2.Servlet Container란?
서블릿 컨테이너란 서블릿을 담아서 관리해주고 작동시키는 집합체를 의미한다. 대표적으로 톰캣(Tomcat)이 있다. 서블릿 컨테이너는 HttpServletRequest, HttpServletResponse 두 객체를 생성한다.         

- HttpServletRequest : 
http프로토콜의 request정보를 서블릿에게 전달하기 위한 목적으로 사용하며 헤더 정보, 파라미터, 쿠키, URI, URL 등의 정보를 읽어 들이는 메서드와 Body의 Stream을 읽어 들이는 메서드를 가지고 있습니다.

- HttpServletResponse : 
WAS는 어떤 클라이언트가 요청을 보냈는지 알고 있고, 해당 클라이언트에게 응답을 보내기 위한 HttpServleResponse 객체를 생성하여 서블릿에게 전달하고 이 객체를 활용하여 content type, 응답 코드, 응답 메시지 등을 전송합니다.

# 3.Forward / Redirect
Servlet을 통해 페이지를 이동하는 방식은 두 가지 존재한다. Forward와 Redirect다.            

- Forward : request, response를 유지하며 페이지 이동(클라이언트 측에서 호출)       

```java
request.getRequestDispatcher("이동할페이지주소").forward(request값, response값);
```

- Redirect : request, resoponse가 새롭게 생성되며 페이지 이동(서버 내부에서 호출)     

```java
response.sendRedirect("이동할페이지주소");
```


즉, forward는 유저가 요청한 첫 url이 유지되며 페이지가 바뀌는 방식이고, redirect는 유저가 요청한 첫 url에서 새롭게 두번째 url이 요청되며 페이지가 바뀌는 방식이다. forward방식은 url이 바뀌지 않으므로 보안이 필요할 경우 주로 사용되는 방식이다.





#### 한글 깨짐 현상
깨진 한글을 복원하는 방법은 GET과 POST방식에 따라 다르다. GET방식은 요청정보 헤더 URI에, POST방식은 요청정보 몸체에 포함되어 전달되기 때문이다.
- post : ServletRequest에서 제공하는 setCharacterEncoding() 메소드 사용
- get
    - html파일에서 <meta charset="UTF-8"> 설정
    - 서버의 환경설정 파일 중 'server.xml'파일에 인코딩 문자코드를 설정

    ```xml
    <Connector connectionTimeout="..."
            port="..."
            protocol="..."
            redirectPort="..."
            URIEncoding="UTF-8" />
    ```


#### 줄 바꿈
servlet에서 줄바꿈 = <br>을 같이 프린트해줘야 브라우저에서 줄 바뀜

#### servlet에서 parameter와 attribute의 차이
- parameter :  get 가능		String반환 		즉, client가 브라우저에 입력한 정보를 의미
- attribute :  set,get 가능		Object반환		즉, servlet(server)에서 만들어진 정보를 의미