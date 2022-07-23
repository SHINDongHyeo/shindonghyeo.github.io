---
author_profile: true
categories: "spring"
toc: true
toc_sticky: true
---

# 1.스프링(Spring)이란?
스프링은 말 그대로 개발자들에게 겨울이 지나고 봄과 같은 세상이 왔다는 의미로 이름이 붙여졌다. 스프링은 스프링 프레임워크라고도 불리며 **자바 기반의 웹 어플리케이션을 만들기 위한 프레임워크**를 의미한다. 프레임워크란 반복작업들을 최소화하기 위해 반복되는 부분에 대한 도구를 미리 만들어 놓은 도움장치라고 생각할 수 있다. 즉, 스프링(스프링 프레임워크)는 자바

## 1.1 스프링 특징
- WAS가 내장되어 있음
- 경량 컨테이너로 자바 객체 관리
- IOC / DI




# 2.스프링 부트(Spring Boot)란?
spring boot는 spring 상위호환이라고 볼 수 있다. 간단히 spring과 spring boot의 차이점을 알아본다.           

- 비동기적인 방식을 쉽게 이용할 수 있다

```java
ex)
@RestController
public class 컨트롤러클래스명{
    @GetMapping("주소")
    public String 메서드명() {
		return "확인!";
	}

}
// 다음 코드에서 "확인!"이라는 문자열을 비동기적인 느낌으로 반환해준다!!!!!
```                 

- dependency설정이 짧아졌다
- configuration설정이 짧아졌다
- 내장서버가 있어서 서버 구동시간이 단축되었다
- jar파일로 간단한 배포 가능하다
- 개발환경 특징 : application.property 와 pom.xml 내용이 매핑된다. pom에는 있는데 application에 해당 설정이 없을 경우 오류 발생 가능!
- src/main/resources 하단 banner.txt 라는 이름의 파일을 만들면 스프링부트 실행 시 콘솔창에 원하는 내용(배너)를 출력할 수 있게 해준다. (그냥 banner.txt 에 문자입력하면 그대로 출력됨)



# 3. 단위테스트
네트워크, 데이터베이스 등이 엮인 상태에서 특정 구역만 단위 테스트하기에는 어려움이 있다. 이를 해결하기 위한 것이 MOCK. 브라우저 없이 서버 없이 API만 이용해 get/post 방식으로 http 통신 가능한 방식

```java
>>>실행
mock.perform(get("/"))
    .andExpect(status().isOk())
    .andDo(print());

mock.perform(get("/param").param("id", "data"))      // parameter값 넘겨주기
    .andExpect(status().isOk())
    .andDo(print());

mock.perform(get("/validate"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("playdata"))
		.andDo(print());
```



# 4. 예외처리
@ExceptionHandler로 exception을 잡아서 처리가능하다


```java
@GetMapping("employee")
public 반환타입 클래스명 throws Exception{
   // 핵심 로직
   // 만약 여기서 Exception이 발생한다면?
}

// 여기서 Exception을 받음!
@ExceptionHandler
public void handler(Exception e) {
    // 원하는 처리 가능
    System.out.println("오류발생!!!");
    e.printStackTrace();
}

```



# 5. Spring Data JPA
인터페이스를 구현하고 @EnableJpaRepositories 어노테이션을 이용해 basePackages로 설정해주면 미리 만들어진 메서드명 생성 문법만 맞춰서 메서드를 만들면 알아서 DB연결, sql문 실행 등을 해주는 스프링 기능

- insert : save(엔티티 객체)
- select : findById(기본키)
- update : save(엔티티 객체)
- delete : deleteById(기본키), delete(엔티티 객체)

이 밖에 메서드명에 넣는 요소
- Containing : where에서 like연산자 %원하는내용% 역할
- And : where에서 and 역할
- IgnoreCase : 대소문자 구별 안하겠다는 의미
- Between : where에서 between 역할
- OrderByIdDesc : 정렬(Asc로 바꾸면 역으로 정렬)