---
author_profile: true
categories: "spring"
toc: true
toc_sticky: true
---

# 1.스프링(Spring)이란?
스프링은 스프링 프레임워크라고도 불리며 **자바 기반의 웹 어플리케이션을 만들기 위한 프레임워크**를 의미한다.

## 1.1 스프링 특징
- WAS가 내장되어 있음
- 경량 컨테이너로 자바 객체 관리
- IOC / DI




# 2.스프링 부트(Spring Boot)란?
spring framework에 비해 더 간단해짐. spring framework가 @Controller로 동기적인 방식을 이용했다면 spring boot에서는 @Controller외에 @RestController도 사용하여 비동기적인 방식을 사용할 수 있다.

ex)
@RestController
public class 컨트롤러클래스명{
    @GetMapping("주소")
    public String 메서드명() {
		return "확인!";
	}

}
다음 코드에서 "확인!"이라는 문자열을 비동기적인 느낌으로 반환해준다!!!!!






개발환경 특징
application.property 와 pom.xml 내용이 매핑된다. pom에는 있는데 application에 해당 설정이 없을 경우 오류 발생 가능!




src/main/resources 하단 banner.txt 라는 이름의 파일을 만들면
스프링부트 실행 시 콘솔창에 원하는 내용(배너)를 출력할 수 있게 해준다. (그냥 banner.txt 에 문자입력하면 그대로 출력됨)