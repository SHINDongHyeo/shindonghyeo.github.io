# 1. Exception
흔히 에러라고 볼 수도 있지만 진짜 Error와는 다른 표시다. <u>Error는 심한 오류로 프로그램 자체가 실행될 수 없을 때 나오는 경고표시고, Exception은 그에 비해 간단한 에러를 의미한다.</u>
## 1) 자바에서 특징
Exception이라는 것은 자바스크립트, 파이썬 등등에도 존재한다.    
자바에서의 Exception은  API에서 제공하는 로직들의 예외상황까지 고려한다.    
ex)       
```java
java.lang.Integer클래스
			public static int parseInt(String v) throws NumberFormatException{
				문자열 받아서 int로 변환가능한지 포맷 확인
				가능하면 int로 변환
			}
```
이런 Integer클래스와 이런 parseInt메서드가 있다면 실제 메서드를 호출할 때 발생할 수 있는 오류들이 있다. 만약 파라미터값 String v에 "삼"을 넣는다면 이는 int로 변환이 되지 않기 때문에 오류가 생길 것이다. 그때 자바는 NumberFormatException이라는 Exception이 발생한다.

ex)     
```java
String s;
System.out.println(s.length());
		
A a
a.m(); //A클래스에 m이라는 메서드가 있다고 가정
```
s라는 String객체를 선언한 뒤 s객체에 length메서드를 호출한다면 이는 잘못된 객체에 대한 메서드 호출이다. 또한 밑에 A a로 클래스 A의 객체 a를 선언했지만 new 생성자를 이용해 객체를 만들지 않았기 때문에 객체가 없는 상태에서 m메서드를 호출하는 모양이 된다. 그때 자바는 NullPointerException이라는 Exception이 발생한다.


## 2) try,catch 예외처리기능
문제가 생겼을 때 아예 프로그램이 멈춘다면 문제가 생길 수 있다. 이를 피하기 위한 것이 바로 예외처리다. 자바에서 이런 예외처리를 강제하기도 한다.   

ex)      
```java
package step01.exception;
class B {
    static{
        System.out.println("B클래스 static으로 먼저 로딩완료");
    }
}

public class ExceptionTest{
    static void m{
        try {
			Class.forName("step01.exception.C");
		} catch (ClassNotFoundException e) {
			System.out.println("로딩하려는 클래스는 없음");
		}
    }
}
```
try부분에서 forName메서드를 호출해 인자로 "step01.exception.C"를 부르려고 했지만 위에서는 클래스C가 아닌 클래스B가 선언되어 있다. 그러므로 class를 찾지 못했다는 ClassNotFoundException을 발생시키는데, try부분 밑에 catch를 확인하면 ClassNotFoundException이 발생하면 이를 포착해 명령을 내리는 부분을 확인할 수 있다. 그러므로 위 코드는 최종 "로딩하려는 클래스는 없음"를 출력한다.     

※ 위에서 클래스B를 선언할 때 static{} 명령어를 확인할 수 있는데, <u>이는 이름이 없는 구조로 가장 큰 특징은 main메서드보다 먼저 실행이 된다는 점이다. 또한 이름이 없기 때문에 명시적 호출을 불가하여 처음에 실행되고 다시 실행되지 않는다.</u> 이런 특징을 이용해 서버 내부에 모든 유저들이 공유하는 자원을 단 한번 초기화할 때 사용되기도 한다.

## 3) RuntimeExeption
만약 최종적으로 출력된 Exception종류의 부모클래스에 RuntimeExeption이 있는 경우와 없는 경우가 다르다. <u>RuntimeExeption이 부모클래스로 있을 경우 컴파일은 되지만 컴파일된 파일을 실행했을 때 오류가 생길 수 있다. 하지만 RuntimeExeption이 부모클래스로 없는 경우는 컴파일은 되지만 컴파일된 파일을 실행했을 때 오류가 생길 수 없다.</u> 이 경우에는 컴파일부터 오류가 생긴다.     
이 부분이 위에서 말한 try,catch를 강제한다는 부분을 설명한다. 만약 RuntimeExeption이 부모클래스로 있어 컴파일은 되는 경우 코드를 작성하는 부분에서는 try,catch를 사용하지 않아도 된다. 하지만 RuntimeExeption이 부모클래스로 있어 컴파일도 되지 않는다면 try,catch를 사용하여야 코드 작성이 가능하다.

## 4) Throw
직접 오류에 대한 정의를 할 수도 있다. 만약 어떤 함수에 대한 인자를 1과 2만 받고 나머지 값이 들어올 경우 오류처리하는 과정을 생각해본다. 1과 2가 인자로 들어갈 경우 정상적인 흐름을 따라가게 하고, 이 외의 경우는 사용자정의오류를 발생시키고 이를 캐치해서 오류에 대한 작업을 진행하면된다. 이때 필요한 부분이 <u>사용자정의 오류 클래스 만들기, 사용자정의 오류 캐치하기</u>다.     
ex)     
```java
package mvc.exception;

public class MvcException extends Exception{
	public MvcException() {
	}

	public MvcException(String m) {
		super(m);
	}
}
```
먼저 mvc.exception패키지에 public으로 MvcException이라는 클래스를 선언하고 부모클래스로 Exception클래스를 상속받았다. 안에는 부모클래스의 메서드를 빌려와서 m인자를 받으면 m을 출력할 수 있도록 했다.
```java
package mvc.view;

import mvc.controller.Controller;
import mvc.exception.MvcException;

public class StartView {
	public static void main(String[] args) {
        System.out.println("1과 2가 아닌 데이터 요청(5요청으로 가정)");
		try {
			Controller.reqRes(5);
		} catch (MvcException e) {
			e.printStackTrace();//
			System.out.println("오류발생");
		}
    }
}
```
mvc.view패키지에 public으로 StartView라는 클래스를 선언하고 main메서드를 실행했다. 클래스 안에는 try,catch를 이용해 Controller클래스의 reqRes메서드를 호출하고 만약 MvcException( 전 단계에서 선언한 사용자정의 오류 클래스)가 호출되면 오류내역과 "오류발생"이라는 문구를 출력하도록 했다.
```java
package mvc.controller;
import mvc.exception.MvcException;
public class Controller {

	public static void reqRes(int reqNo) throws MvcException{
        if(reqNo == 1) {  
			// 정상동작코드작성
			
		}else if(reqNo == 2) {
			// 정상동작코드작성

		}else {
			System.out.println("예외처리부분시작");
			throw new MvcException("예외처리thrownew확인용");
		}
    }
}

```
mvc.controller패키지에 public으로 Controller라는 클래스를 선언하고 public static으로 reqRes메서드를 작성했다. 내용으로는 인자가1, 2들어올 경우와 이외의 경우 이렇게 3가지로 나누었고 1,2가 아닌 인자가 들어올 경우는 "예외처리부분시작"을 출력하고 <u>throw new MvcException("예외처리thrownew확인용")을 통해 인자를 출력해주는 MvcException생성자를 던져주었다. 그리고 MvcException을 던지기 위해 reqRes메서드 선언 이후에 throws MvcException를 작성해주었다.</u> 던져진 MvcExcpetion은 두번째 작성한 코드 StartView클래스에서 catch로 인해 잡혀 오류내역과 "오류발생"문구를 출력하게 된다.





# @부가내용
1. 하나의 .java 파일 안에 여러 클래스를 선언하는 경우가 있는데, 이때 .java 파일을 컴파일하고 bin폴더를 열어 .class로 컴파일된 파일을 확인하면 선언된 클래스 별로 따로따로 .class 파일로 컴파일된 모습을 확인할 수 있다.       


2. 값비교    
- 참조타입
    - 주소값 비교 : ==
    - 서로 다른 객체 간 내용값 비교 : public boolean equals(타입 변수){ }   
- 기본타입
    -  값 비교 : ==


3. 조건식에 and를 넣고 싶다면 : &&     
ex) if (조건식1 && 조건식2){}      
이때 조건식1이 만약 false라면 조건식2가 true/false인 두 경우 모두 결국 false이므로 조건식2는 확인도 하지 않는다.
