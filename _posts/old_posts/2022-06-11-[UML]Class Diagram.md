---
category: CS
layout: post
---

# UML이란?
UML(Unified Modeling Language)은 통합 모델링 언어로 객체 지향 프로그램을 개발할 때 시각적 모델을 만들기 위해 쓰이는 표준화된 언어다. UML은 구조 다이어그램(Struncture Diagram)과 행위 다이어그램(Behavior Diagram)으로 나뉜다. 
- 구조 다이어그램 : 프로그램의 정적인 구조를 보여주는 형식이다. 
- 행위 다이어그램 : 프로그램의 객체 간 동적인 작동을 보여주는 형식이다.

# Class Diagram
UML 구조 다이어그램의 한 종류로 클래스들 사이의 관계를 시각화해 표현한 것을 의미한다.

## 1.Class 구성
클래스는 3부분으로 나뉜다. 클래스명, 변수명, 메서드 3부분이다. 변수와 메서드의 접근제어자는 '+,-,#,~'를 이용해 표현하고 변수타입과 메서드 리턴타입은 ':'콜론을 이용해 표현한다. 메서드의 파라미터는 타입만 작성한다.
- \+ : public 모든 클래스에서 접근 가능
- \- : private 해당 클래스와 객체만 접근 가능
- \# : protected 해당 패키지에 있거나 상속관계에 있는 하위 클래스 객체만 접근 가능
- ~ :  package 해당 패키지에 있는 클래스와 객체 접근 가능        
- 변수나 메서드에 밑줄 : static 해당 변수나 메서드를 객체생성없이 호출가능
- 이텔릭체 : Abstract class(추상 클래스)
- << \interface >> : Interface(인터페이스)        
ex)          
<img width="291" alt="클래스구성" src="https://user-images.githubusercontent.com/96512568/173172819-ee62326c-b26c-46ad-a4f9-af6512f009f8.png">


## 2.Class 관계
- 다중성 표시 : 클래스만으로 여러 객체간 관계를 표현할 수 있는 표시
    - 없음 : 일대일 관계
    - 1 : 객체 1개만 생성
    - \* 혹은 0..\* : 객체 0개 또는 0개 이상 생성
    - 1..\* : 객체 1개 이상 생성
    - 0..1 : 객체 0개 또는 1개 생성
    - 1,2,3 : 객체 1개 또는 2개 또는 3개 생성
    - ex) 대장객체 1개가 부하객체 6~10개를 관리 : 대장클래스(1)----->부하클래스(6..10)

- 연관관계( association ) : 보통 한 클래스가 다른 클래스가 제공하는 기능을 사용하는 관계를 의미한다.
    - V 실선 화살표 : 단방향 연관관계로 A와 B가 있을 때 A는 B에 대해 알고, B는 A에 대해 모르는 관계다. 즉,클래스A가 클래스B를 멤버변수로 가지고 있는 관계다. 이해하기 쉽게 A가 B를 소유하고 있는 주종관계를 생각하면 된다.      

    - 실선 : 양방향 연관관계로 A와 B가 서로에 대해 알고있는 관계다. 이해하기 쉽게 A와 B가 친구인 상태를 생각하면 된다.

- 일반화관계( generalization)
    - Δ 속이 빈 실선 화살표 : 상속관계를 표현하고 부모 클래스는 추상적 개념으로 실제로 존재하지는 않는다. 자식 클래스는 실제로 존재하고 부모 클래스의 변수나 메서드를 제공받는다. 이해하기 쉽게 부모 클래스를 사람(실존하는 사람 자체가 아닌 사람이라는 카테고리를 의미) 자식 클래스를 유재석이라고 생각하면 된다.

- 집합관계 : 전체와 부분의 관계를 의미한다.
    - 집약관계( aggregation )
        - 속이 빈 실선 마름모 : 전체와 부분이 서로 의존적이지 않은 관계, 부분 객체들을 다른 객체와 공유가능하며 전체 객체를 메모리에서 삭제해도 부분 객체는 삭제되지 않는다. 이해하기 쉽게 분리형 배터리와 휴대폰을 생각하면 된다.

    - 합성관계( composition )
        - 속이 찬 실선 마름모 : 전체와 부분이 서로 의존적인 관계, 부분 객체들을 다른 객체와 공유불가하며 전체 객체를 메모리에서 삭제하면 부분 객체도 삭제된다. 이해하기 쉽게 일체형 배터리와 휴대폰을 생각하면 된다.

- 의존관계( dependency )
    - V 점선 화살표 : 연관관계 실선 화살표와 비슷하지만 다른점은 클래스A가 클래스B를 멤버변수로 가지고 있는게 아니라 클래스A가 클래스B의 메서드를 멤버변수로 가지고 있어 메서드를 실행하는 동안만 짧게 관계가 이어진다.

- 실체화관계( realization )
    - interface : 책임이라는 의미로 어떤 객체가 해야하는 일 또는 할 수 있는 일을 의미한다. 클래스 다이어그램에서 클래스명 위에 '<<interface>>'라고 작성해 표현한다.
    - Δ 속이 빈 점선 화살표 : 일반화관게 Δ 속이 빈 실선 화살표와 비슷하지만 다른점은 일반화관계가 카테고리와 카테고리에 속한 객체의 관계라면, 실체화관계는 기능과 기능을 수행할 수 있는 객체의 관계다.


# 내 생각
class 관계로 설명된 개념들 중 연관관계와 집합관계(집약관계, 합성관계)의 차이가 거의 없다고 느꼈다. 두 관계 모두 한 클래스가 다른 클래스를 멤버변수로 가지고 있는 관계를 의미하기 때문이다. 약간의 차이는 집합관계는 생성자를 이용해 다른 클래스 멤버변수들을 생성하거나 파라미터로 받는다는 점(?)같다. 내가 잘못 이해한 것인지 이런 개념들 자체가 원래 모호한 것인지는 모르겠다.