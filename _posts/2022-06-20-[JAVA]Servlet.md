---
author_profile: true
categories: "java"
toc: true
toc_sticky: true
---

# 1.Servlet이란?
서블릿이란 **자바를 사용하여 웹을 만들기 위한 기술**이다. 더 정확히 말하면 웹에서 발생되는 요청(Request)과 응답(Response)을 쉽게 관리할 수 있도록 해준다. 간단하게 웹을 이용해 유저와 소통하는 단계를 표현해본다.          
ex) 유저(웹) -> html/servlet/jsp(유저로부터 요청을 받음) -> 자바/DB (필요한 작업을 통해 데이터 가공) -> html/servlet/jsp(유저에게 응답을 보냄) -> 유저(웹)          

# 2.Servlet Container란?
서블릿 컨테이너란 서블릿을 담아서 관리해주고 작동시키는 집합체를 의미한다. 대표적으로 톰캣(Tomcat)이 있다. 서블릿 컨테이너는 HttpServletRequest, HttpServletResponse 두 객체를 생성한다.         

- HttpServletRequest : 
http프로토콜의 request정보를 서블릿에게 전달하기 위한 목적으로 사용하며 헤더 정보, 파라미터, 쿠키, URI, URL 등의 정보를 읽어 들이는 메서드와 Body의 Stream을 읽어 들이는 메서드를 가지고 있습니다.

- HttpServletResponse : 
WAS는 어떤 클라이언트가 요청을 보냈는지 알고 있고, 해당 클라이언트에게 응답을 보내기 위한 HttpServleResponse 객체를 생성하여 서블릿에게 전달하고 이 객체를 활용하여 content type, 응답 코드, 응답 메시지 등을 전송합니다.