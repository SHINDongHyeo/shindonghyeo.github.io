---
author_profile: true
categories: "java","etc"
toc: true
toc_sticky: true
---

# 1. SingleTon
객체생성에 제한을 걸기 위한 방법이다.  

## 1.1 Eager Initialization(Early Loading) (가장 기본적 singleTon패턴)
ex)

```java
public class SingleTon {
	private static SingleTon instance = new SingleTon();
	private SingleTon() {}
	
	public static SingleTon getInstance() {
		return instance;
	}
}
```

1. private SingleTon() {}    
먼저 기본생성자를 private처리하여 SingleTon외부에서 아무리 SingleTon클래스를 import해도 SingleTon객체를 못하게 만든다.
2. private static SingleTon instance = new SingleTon();     
즉 SingleTon객체는 SingleTon클래스 안에서만 만들 수 있으므로 클래스명을 instance로 하는 SingleTon객체를 하나 만들고, 이 작업또한 외부에서 쓰지 못하도록 private처리한다.(static설명은 뒤에)    
3. public static SingleTon getInstance() {    
		return instance;    
	}    
클래스 밖에서 객체를 생성하지는 않지만 객체를 받아서 사용해야 하기 때문에 public으로 SingleTon클래스 getInstance메서드를 선언해준다. 리턴값은 instance를 받을 것이다. **이때, 객체를 선언하지 않으면 해당 클래스의 메서드를 불러올 수 없는 상황이 문제를 일으킨다.** 객체를 여러개 생성하지 않기 위해 위에서 private으로 생성자를 막는 행위를 했는데, 객체가 없어서 메서드를 호출하지 못하는 것이다. **하지만 해당 메서드가 static이라면 다르다. static 메서드는 객체 생성과 무관하게 호출될 수 있고, 호출되면 메모리의 클래스(메서드) 영역에 저장되기 때문에,** getInstance메서드에 static을 적용한다.     
4. 따라서 2번에서 설명하지 않았던 static도 따라 붙는다. 왜냐하면 getInstance메서드가 static으로 생성되어 반환값으로 instance를 받는데, instance도 static이어야 반환받을 수 있기 때문이다.

### 1.1.1 기본적인 singleTon의 문제점    
**클라이언트에서 사용하지 않는 경우에도 인스턴스가 생길 수 있다**      
위에 제공된 코드는 사실 호출될 수 있는 요소가 getInstance메서드 밖에 존재하지 않아서, getInstance메서드가 호출되지 않는한 사용되지 않는 singleTon클래스는 로드되지 않아 클래스 안에 static요소들도 초기화되지 않는다고 한다. 하지만 만약 getInstance메서드 이외에 다른 static메서드가 있고 이 메서드가 다른 곳에서 호출이 된다면 자동으로 singleTon클래스도 로드되면서 singleTon객체가 생성되게 된다. 즉, singleTon객체 생성이 필요하지 않더라도 해당 클래스의 다른 메서드를 썼다고 객체가 미리 생성되게 되는 상황이다.       

이를 해결하기 위한 방법 --> **Lazy Initialization**

## 1.2 Lazy Initialization
ex)          

```java
public class Singleton {
    private static Singleton instance;     //---> Eager Initialization과 달리 여기서 생성자를 통해 객체를 만들지 않는다.
    
    private Singleton() {}
    
    public static Singleton getInstance(){
        if(Objects.isNull(instance)) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

Eager Initialization와 달리 생성자를 getInstance메서드 안으로 넣어버려서 getInstance메서드가 호출되어야만 singleTon객체가 생성되게 된다.        
--> **SingleTon클래스의 다른 메서드나 필드가 호출되었을 때 SingleTon객체가 미리 생성되는 문제점 해결!**

### 1.2.1 Lazy Initialization 문제점
**스레드 세이프하지 않다**        
이런 singleTon패턴의 문제점은 '멀티스레드'환경에서 발생한다. 멀티스레드는 간략하게 설명하면 여러 스레드를 통해 각 스레드가 각자 따로 움직이도록 만들어 하나의 프로그램이 동시에 여러일을 할 수 있게 하는 방법이다. 이런 환경에서 스레드1과 스레드2가 동시에 getInstance메서드를 호출하게 되면 동시에 instance를 생성하게 되어 유일무이하지 않은 객체 2개가 생성된다.


## 1.3 Lazy Initialization의 해결책
### 1.3.1 Double Checked Locking(DCL)         
말 그대로 두 번 체크하는 방식이다. 체크에는 synchronized가 사용되고 데이터의 읽기,쓰기를 메인메모리만이 관리하도록 volatile을 이용한다.     
- synchronized : **스레드 세이프하게 만들기 위한 방법이다.** 첫번째 체크에서는 객체의 유무를 판단해 객체가 없으면 synchronized를 실행해 다른 스레드를 막아 유일무이한 singleTon객체를 만드는 작업을 하고, 객체가 있으면 이미 있는 객체를 바로 반환해준다. 두번째 체크에서는 첫번째 체크에서 객체가 없는 경우에서 일어나는 체크로 연산의 원자성(?)을 유지하기 위해 같은 작업을 반복해준다.     
- volatile : synchronized는 객체생성이 여러번되는 경우를 배제하기 위해 사용되었다면, volatile은 유일무이한 singelTon객체를 멀티스레드를 활용해 값을 변경하고 다시 대입해주는 과정에서 아직 변경된 내용이 저장되지 않은 singleTon객체를 다른 스레드가 불러내는 경우를 배제하기 위해 사용된다. 즉, singleTon객체의 데이터를 수정하거나 다시 저장하는 작업을 여러 스레드가 각자의 캐시 메모리로 따로 진행하는 것이 아닌 메인메모리 하나가 모든 수정을 통제하여 수정내용이 엇갈리지 않게
하는 명령어이다.     


```java
public class SingleTon {
	private static volatile SingleTon instance = new SingleTon();
	private SingleTon() {}
	
	public static SingleTon getInstance() {
		if (instance == null){ //-----------------> 첫번째 체크
			synchronized (SingleTon.class) {
				if (instance == null){ //-----------------> 두번째 체크
					instance = new.SingleTon();
				}
			}
		}
		return instance;
	}
}
```

### 1.3.2 Bill Pugh Solution
자바에 프로그램이 처음 실행될 때 class가 classloader에 의해 synchronized가 자동 실행되도록 설계되어있다. 이를 이용해 직접 synchronized 구문을 작성하지 않고 singelTon으로 만들고 싶은 클래스의 생성자 자체에 'private static class' 처리를 해주는 것이다. 마치 class로 다시 덮어주는 모습과 비슷해 holder라고도 부른다.       

- private : singleTon으로 외부에서 불러올 수 없게 설정
- static : 프로그램이 시작될 때 바로 생성됨
- class : 자동 synchronized가 적용됨       
 

```java
public class SingleTon {
	private static class SingleTonHolder{ //--------> 'private static class' 클래스 구조로 감싸기
		private static SingleTon instance = new SingleTon();
	}
	private SingleTon() {}
	
	public static SingleTon getInstance() {
		return SingleTonHolder.instance;   //--------> SingleTon클래스가 아닌 SingleTonHolder클래스의 인스턴스임
	}
}
```

문제점 : **클라이언트가 임의로 singleTon파괴가 가능한 구조다. 리플렉션   직렬화+역직렬화**

#### 1.3.2.1 Refelction

아직 공부 안 함

#### 1.3.2.2 직렬화+역직렬화

아직 공부 안 함

### 1.3.3 Enum
Enum은 lazy initializaion의 문제점인 스레드 세이프하지 않음을 해결하지만, lazy loading을 할 수 없어 lazy initialization의 장점은 살리지 못했다. 하지만 스레드 세이프함 외에도 직렬화/역직렬화에 대한 문제점을 해결하여 안전한 패턴이다.

```java
public enum SingleTon {
	instance;
}
```

문제점 : **싱글턴 해제 번거로움, Enum외 클래스 상속 불가**



# 2.분석을 통한 정리
수업 때 사용한 코드를 분석해 정리해보았다.   
![517정리](https://user-images.githubusercontent.com/96512568/168758601-65e85a90-4a67-4a9e-87e0-ca8f362cc424.jpg)


