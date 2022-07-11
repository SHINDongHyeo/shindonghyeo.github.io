---
author_profile: true
categories: "spring"
toc: true
toc_sticky: true
---


# 1.AOP란?
AOP(Aspect Oriented Programming)란 관점 지향 프로그래밍 기법이다. 어떤 관점으로 프로그래밍을 할까? **핵심로직이냐, 부가로직이냐가 바로 그 관점**이다. **로직들을 핵심내용인지 부가내용인지에 대한 관점으로 나누고 나뉜 파트들 중 공통된 부분들은 하나로 묶어 모듈화시키는 기법**이 바로 AOP다. 일반적으로 프로그래밍 언어들은 OOP(Object Oriented Programming)라는 객체 지향 프로그래밍 언어다. 로직을 객체 단위로 묶어서 개발하는 방식이고 일반적으로 이 방법이 가장 친근할 것이다. AOP는 이런 OOP와 상호보완 관계를 갖는다. 예제를 통해 확인해본다.         

**예제)**             
일반적인 OOP객체지향프로그래밍 클래스들과 구성내용                  
클래스A : 시작메시지, A만의 핵심로직, 끝메시지        
클래스B : 시작메시지, B만의 핵심로직, 끝메시지       
클래스C : 시작메시지, C만의 핵심로직, 끝메시지        

AOP로 **공통적인 부가기능을 Aspect라는 단위로 모듈화**한다면?         
클래스A : A만의 핵심로직             
클래스B : B만의 핵심로직             
클래스C : C만의 핵심로직             
Aspect시작메시지 : 시작메시지            
Aspect끝메시지 : 끝메시지          

이렇게 **핵심로직과 부가로직을 분리하여 재사용할 수 있게된다.**        

## 1.1 AOP 개념(단어)
- Concern : '관심사'라고도 불리고 위 예제에서 부가로직을 의미한다. 여러 객체에서 반복적으로 쓰이는 부가기능 로직. 이때 부가로직들은 여러 객체에서 나뉘어 있으므로 흩어진 관심사(Croscutting COncerns)라고 부르기도 한다.
- Aspect : 흩어진 관심사를 하나로 묶어서 모듈화한 것을 의미한다.
- Advice : 실질적인 부가기능을 담은 구현체를 의미한다.
- JointPoint : Advice가 적용될 위치로 부가기능이 핵심기능 전에 실행될지, 후에 실행될지, 리턴값을 받고 실행될지 등의 옵션이 존재한다.
- PointCut : JointPoint의 상세한 스펙을 정의한 것으로 부가기능을 넣어줄 핵심기능을 명확하게 해준다.
- Target : Aspect가 적용될 클래스나 메서드를 의미한다.

## 1.2스프링 AOP설정
1. AOP는 스프링 자체에서 제공하는 기능이 아니다. 그러므로 pom.xml에 다음과 같은 의존성을 추가해서 사용한다. 또한 AOP를 더 쉽게 사용하기 위해 어노테이션 의존성도 추가해준다.

```xml
<!-- AOP 적용시에 필요한 추가 library - byte code를 실시간 동적으로 자동 생성해주는 기능의 library -->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>2.2.2</version>
</dependency>

<!-- AOP 기능의 framework - spring이 AOP 기술을 활용해서 spring 스럽게 활용 -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.7.3</version>
</dependency>

<!-- AOP 기능의 어노테이션 사용 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>4.3.30.RELEASE</version>
</dependency>
```

2. Spring Bean Configuration file 설정          
Configure Namespaces에서 'aop, beans, context'체크박스를 체크해서 사용         

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    
    ...

</beans>
```

3. Spring Bean Configuration file 내용작성           

```xml
<!-- AOP기법 사용하겠다 -->
<aop:aspectj-autoproxy/>

<!-- 어노테이션 기능 사용하겠다 -->
<context:annotation-config/>

<!-- 어노테이션 기능을 위해 해당 package를 스캔해 component를 찾겠다 -->
<context:component-scan base-package="package명"/>
```

만약 어노테이션 기능을 쓰지 않는다면 다음과 같이 설정해준다.

```xml
<aop:config>
    <aop:poincut> : expression="execution(**실행될 메서드,클래스 위치**)" / id="**해당포인트컷 id**"
        <aop:aspect> : ref="**aspect로 쓸 bean id**" 
            <aop:after> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**"
            <aop:before> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**"
            <aop:after-returning> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**" / returning="**return한 값의 변수명**"
            <aop:after-throwing> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**" / throwing="**throw한 예외의 변수명**"
        <aop:aspect/>
    <aop:poincut/>
<aop:config/>
```

## 1.3스프링 어노테이션(+ AOP 어노테이션)
- @Configuration : 해당 class가 bean 구성임을 알려준다.
- @Bean : 개발자가 직접 제어 불가능한 class를 bean으로 등록한다.
    - name : bean id 설정( 디폴트값 : 클래스명의 카멜표기법 )
- @Component : 개발자가 직접 작성한 class를 bean으로 등록한다.
    - value : bean id 설정( 디폴트값 : 클래스명의 카멜표기법 )
- @Aspect : 해당 클래스를 부가기능 class(Aspect)로 등록한다.
    - @After : 핵심기능(Target) 이후 해당 부가기능(Advice) 실행
        - pointcut = "execution(* 패키지나 클래스나 메서드특정)" : Target 지정
    - @Before : 핵심기능(Target) 이전 해당 부가기능(Advice) 실행
       - pointcut = "execution(* 패키지나 클래스나 메서드특정)" : Target 지정
    - @AfterReturning : 핵심기능(Target)에서 return 이후 해당 부가기능(Advice) 실행
        - pointcut = "execution(* 패키지나 클래스나 메서드특정)" : Target 지정
    - @AfterThrowing : 핵심기능(Target)에서 throw 이후 해당 부가기능(Advice) 실행
        - pointcut = "execution(* 패키지나 클래스나 메서드특정)" : Target 지정
    - @Around : 핵심기능(Target) 전, 후로 해당 부가기능(Advice) 실행
        - pointcut = "within(패키지나 클래스나 메서드특정)" : Target 지정
- @Scope : 말그대로 scope설정한다.( 디폴트값 : singleton )
- @Lazy : 해당 bean이 사용될 때 객체 뒤늦게 생성되도록 설정한다.
- @Autowired : 의존성 주입을 가능케 한다. 클래스 안에 멤버변수가 클래스 생성과 같이 생성되어야 하는 의존성을 가지고 있으면 autowired를 해당 멤버변수에 설정해 클래스가 생성되면 같이 생성되게 할 수 있다.
- @Qualifier("bean명") : autowired에서 다형성을 지닌 멤버변수에 autowired 설정을 해준다면? (멤버변수가 인터페이스고, 인터페이스를 구현한 클래스가 여러개 있다) 이때 어떤 구현 클래스가 들어올지 모르기 때문에 이를 확정지어주어야 한다. 이때 qualifier에서 구현클래스를 명확히 알려줘서 해결한다.
- @Repository : DAO용도의 class에만 적용 권장된다. 이름에 의미를 부여해서 사용을 권장한다
선언된 package를 scan tag로 등록한다.
- @Service : DAO와 Controller 사이 Service클래스라는 것을 알려주기 위해 사용된다.
















# 2.Spring MVC

## 2.1 Controller

### 2.1.1 RequestMapping
해당 메서드의 url을 설정. 즉, view단에서 해당 메서드를 호출할 때 주소 설정

### 2.1.2 String반환타입 메서드와 return "주소";
- return "주소"; : 해당 메서드의 request를 보낼 주소를 설정한다. "forward:주소", "redirect:주소" 로 원하는 방식 설정이 가능하고 적지 않을 경우 디폴트로 forward방식을 사용하고, servlet-mapping에 대한 설정을 해놓았을 경우(prefix, suffix 설정같은 것들) 해당 설정대로 적용된다.

### 2.1.3 ModelAndView반환타입 메서드와 return ModelAndView객체;
- @ModelAttribute("attribute변수명") 변수타입 변수명 : response에 attribute로 해당 변수를 "atttribute변수명"이라는 이름으로 저장해서 보내는 것. 이를 통해 response를 받은 jsp단에서 requestScope을 이용해 해당 변수를 이용할 수 있다.
- ModelAndView객체.addObject("변수명","넣을값") : ModelAndView에 해당 변수를 저장한다.
- ModelAndView객체.setViewName("request주소") : request할 주소를 적는다.
- return ModelAndView객체; : ModelAndView객체를 담아서 setViewName에 설정된 주소로 보낸다.

### 2.1.4 Model반환타입 메서드와 return "주소";
- .addAttribute(attribute명, 넣을값) : Model객체에 attribute로 attribute명이라는 이름으로 해당값을 저장해준다.
- return "주소"; : 해당 메서드의 request를 보낼 주소를 설정한다.




### 2.1.5 URI template
가변적인 url을 사용하면서 페이지자체는 같은 페이지를 보여주는 방식

- @RequestMapping("uri주소") : uri주소값으로 중괄호를 이용해 가변적인 값을 표현한다.          
ex) @RequsestMapping("abc/{changingValue}") 라면 abc/1, abc/999, abc/qwe 등등 모든 abc/ 이후 모든 값에 대한 uri를 호출할 경우 실행되는 메서드를 지정하는 모습이다. 하지만 abc/qwe/asd 같이 슬래쉬가 한 번더 생기는 경우는 실행되지 않는다. 다음과 같이 깊이가 깊어진 부분에 대한 가변적 uri처리를 하고 싶다면 @RequsestMapping("abc/{changingValue}/{chagingValue2}") 이런식으로 한 번더 처리해줘야 한다.

- (@PathVariable String changingValue) : 바로 위 예제에서 {changingValue}로 가변적인 uri주소 부분을 표현했다. 이 가변적인 값을 변수로 받고 싶다면 메서드 파라미터 부분에 다음과 같이 해당 변수명으로 가변적인 uri주소값을 받을 수 있다.

### 2.1.6 Cookie, Session Tracking
- 메서드 파리미터로(@CookieValue("key값") String 새로운변수명) : cookie값을 받아와 새로운변수에 저장함
- 메서드 파라미터로(HttpSession 세션변수명) : HttpSession을 파라미터로 받아온다.
- Controller 클래스 선언 구에 SessionAttributes({"key1값", "key2값", ... }) : Session상 attribute값 받아오기
- SessionStatus status : HttpSession으로 세션을 받지 않고 SessionAttributes로 세션을 받았다면 이 세션을 종료시키기 위해 다음과 같이 status 객체를 만들고 status.setComplete();을 통해 세션을 종료시킨다.