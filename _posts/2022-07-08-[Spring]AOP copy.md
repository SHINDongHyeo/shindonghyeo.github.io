---
author_profile: true
categories: "spring"
toc: true
toc_sticky: true
---


# AOP란?
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

# AOP 개념(단어)
- Concern : '관심사'라고도 불리고 위 예제에서 부가로직을 의미한다. 여러 객체에서 반복적으로 쓰이는 부가기능 로직. 이때 부가로직들은 여러 객체에서 나뉘어 있으므로 흩어진 관심사(Croscutting COncerns)라고 부르기도 한다.
- Aspect : 흩어진 관심사를 하나로 묶어서 모듈화한 것을 의미한다.
- Advice : 실질적으로 어떤 일을 해야할 지에 대한 것, 실질적인 부가기능을 담은 구현체
- PointCut : JointPoint의 상세한 스펙을 정의한 것. 'A란 메서드의 진입 시점에 호출할 것'과 같이 더욱 구체적으로 Advice가 실행될 지점을 정할 수 있음
- JointPoint : Advice가 적용될 위치, 끼어들 수 있는 지점. 메서드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용가능
- Target : Aspect를 적용하는 곳 (클래스, 메서드 .. )









- <AOP 구조>
- <aop:config>
    - <aop:poincut> : expression="execution(**실행될 메서드,클래스 위치**)" / id="**해당포인트컷 id**"
        - <aop:aspect> : ref="**aspect로 쓸 bean id**" 
            - <aop:after/before> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**"
            - <aop:after-returning> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**" / returning="**return한 값의 변수명**"
            - <aop:after-throwing> : method="**실행시킬 aspect bean 속 메서드명**" / pointcut-ref="**실행시킬 포인트컷 id**" / throwing="**throw한 예외의 변수명**"





어노테이션
- <context:annotation-config/> : 
- <context:component-scan base-package="step03.aop.biz.annotation, common"/>
@Configuration : class가 bean 구성임을 알려준다.
@Bean : 개발자가 직접 제어 불가능한 class를 bean으로 등록한다
속성
- name = "" : bean id 설정( 디폴트값 : 클래스명의 카멜표기법 )

@Component : 개발자가 직접 작성한 class를 bean으로 등록한다
속성
- value = "" : bean id 설정( 디폴트값 : 클래스명의 카멜표기법 )

@Aspect : 해당 클래스가 부가기능 class다라는 것을 알려줌

@Scope : 말그대로 scope설정( 디폴트값 : singleton)
@Lazy : 해당 빈이 사용될 때 객체 생성



@Autowired : 의존성 주입을 가능케 한다. 클래스 안에 멤버변수가 클래스 생성과 같이 생성되어야 하는 의존성을
가지고 있으면 autowired를 해당 멤버변수에 설정해 클래스가 생성되면 같이 생성되게 할 수 있다.
@Qualifier("bean명") :
autowired에서 다형성을 지닌 멤버변수에 autowired 설정을 해준다면? (멤버변수가 인터페이스고, 인터페이스를 구현한
클래스가 여러개 있다) 이때 어떤 구현 클래스가 들어올지 모르기 때문에 이를 확정지어주어야 한다.
이때 qualifier에서 구현클래스를 명확히 알려줘서 해결한다.



@Repository : DAO용도의 class에만 적용 권장된다. 이름에 의미를 부여해서 사용을 권장한다
선언된 package를 scan tag로 등록한다.
@Service : DAO와 Controller 사이 Service클래스라는 것을 알려주기 위해 사용된다.